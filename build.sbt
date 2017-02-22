import com.typesafe.sbt.site.JekyllSupport
import sbt.Keys._
import sbt._

import scala.io.Source

import com.typesafe.sbt.SbtSite.SiteKeys._

name := "genalgo"

scalaVersion := "2.12.1"

lazy val root = project.in(file(".")).
  aggregate(genalgoJS, genalgoJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val sharedSettings = Seq(
  scalaVersion := "2.12.1",
  crossScalaVersions := Seq("2.12.1", "2.11.8"),
  version := "0.1.4-SNAPSHOT",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)

def resourceGenerator(folder: String, sourceType: String, outputPackage: Seq[String]) = {
  baseDirectory map { dir =>
    val fileToWrite = dir / ".." / "shared" / "src" / folder / "scala" / outputPackage.mkString("/") / "Resources.scala"
    val folderToRead = dir / ".." / "shared" / "src" / sourceType / "resources"

    def sourceForDir(directory: File): String = {
      directory.listFiles().map { file =>
        if (file.isDirectory) {
          s"""object ${file.name} {
              |${sourceForDir(file)}
              |}""".stripMargin
        } else {
          val fileLines = Source.fromFile(file).getLines().toList
          val stringList = fileLines.map(s => '"' + s + '"').toString()
          s"""val ${file.name.split('.').head} = $stringList"""
        }
      }.mkString("\n")
    }

    val toWrite =
      s"""package ${outputPackage.mkString(".")}
         |object Resources {
         |${sourceForDir(folderToRead)}
         |}""".stripMargin
    IO.write(fileToWrite, toWrite)
    Seq(fileToWrite)
  }
}

lazy val genalgoSettings = sharedSettings ++ Seq(
  organization := "me.shadaj",
  name := "genalgo",
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.4.5" % Test,
  testFrameworks += new TestFramework("utest.runner.Framework"),
  sourceGenerators in Compile +=  {resourceGenerator("gen", "main", Seq("me", "shadaj", "genalgo"))}.taskValue,
  cleanFiles += { baseDirectory { base => base / ".." / "shared" / "src" / "gen" }.value },
  sourceGenerators in Test +=  { resourceGenerator("testGen", "test", Seq("me", "shadaj", "genalgo", "tests"))}.taskValue,
  cleanFiles += { baseDirectory { base => base / ".." / "shared" / "src" / "testGen" }.value }
)

lazy val genalgo = crossProject.in(file(".")).
  settings(genalgoSettings: _*).
  jvmSettings(
    libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.12.0",
    libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.23" % Test,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % Test
  ).
  jsSettings(
    jsDependencies += RuntimeDOM,
    jsDependencies += ProvidedJS / "bio-pv.min.js" % Test,
    jsEnv := PhantomJSEnv().value,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  )

lazy val genalgoJVM = genalgo.jvm
lazy val genalgoJS = genalgo.js

lazy val demosSettings = sharedSettings ++ Seq(persistLauncher in Compile := true)

lazy val demos = project.settings(demosSettings: _*).enablePlugins(ScalaJSPlugin).dependsOn(genalgo.js)

site.settings

site.addMappingsToSiteDir(mappings in packageDoc in Compile in genalgoJS, "js/latest/api")
site.addMappingsToSiteDir(mappings in packageDoc in Compile in genalgoJVM, "jvm/latest/api")

site.jekyllSupport()

siteMappings ++= Seq(
  (crossTarget in Compile in demos).value / "demos-opt.js" -> "demos-opt.js",
  (crossTarget in Compile in demos).value / "demos-launcher.js" -> "demos-launcher.js"
)

includeFilter in JekyllSupport.Jekyll ~= { _ || "*.svg" }
