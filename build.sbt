import com.typesafe.sbt.site.JekyllSupport
import sbt.Keys._
import sbt._
import bintray.Plugin.bintraySettings

import scala.io.Source

import scoverage.ScoverageSbtPlugin.ScoverageKeys.coverage

import com.typesafe.sbt.SbtSite.SiteKeys._

name := "genalgo"

lazy val root = project.in(file(".")).
  aggregate(genalgoJS, genalgoJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val sharedSettings = Seq(
  scalaVersion := "2.11.6",
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
  libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % Test,
  testFrameworks += new TestFramework("utest.runner.Framework"),
  sourceGenerators in Compile <+= resourceGenerator("gen", "main", Seq("me", "shadaj", "genalgo")),
  cleanFiles <+= baseDirectory { base => base / ".." / "shared" / "src" / "gen" },
  sourceGenerators in Test <+= resourceGenerator("testGen", "test", Seq("me", "shadaj", "genalgo", "tests")),
  cleanFiles <+= baseDirectory { base => base / ".." / "shared" / "src" / "testGen" }
) ++ bintraySettings ++ bintrayPublishSettings

lazy val genalgo = crossProject.in(file(".")).
  settings(genalgoSettings: _*).
  jvmSettings(
    libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
    libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.10" % Test,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % Test
  ).
  jsSettings(
    jsDependencies += RuntimeDOM,
    jsDependencies += ProvidedJS / "bio-pv.min.js" % Test,
    preLinkJSEnv := PhantomJSEnv().value,
    postLinkJSEnv := PhantomJSEnv().value,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
  )

lazy val genalgoJVM = genalgo.jvm
lazy val genalgoJS = genalgo.js

lazy val demosSettings = sharedSettings ++ Seq(persistLauncher in Compile := true)

lazy val demos = project.settings(demosSettings: _*).enablePlugins(ScalaJSPlugin).dependsOn(genalgo.js)

coverage := {
  coverage.value
  def fileToMkdir = {
    val pathForRoot = new File("").toPath.toAbsolutePath.toString
    s"$pathForRoot/jvm/target/scala-2.11/scoverage-report$pathForRoot/jvm"
  }
  new File(fileToMkdir).mkdirs
}

site.settings

ghpages.settings

git.remoteRepo := s"https://${sys.env.getOrElse("GH_TOKEN", "")}@github.com/shadaj/genalgo.git}"

site.addMappingsToSiteDir(mappings in packageDoc in Compile in genalgoJS, "js/latest/api")
site.addMappingsToSiteDir(mappings in packageDoc in Compile in genalgoJVM, "jvm/latest/api")

site.jekyllSupport()

siteMappings ++= Seq(
  baseDirectory.value / "demos" / "target" / "scala-2.11" / "demos-opt.js" -> "demos-opt.js",
  baseDirectory.value / "demos" / "target" / "scala-2.11" / "demos-launcher.js" -> "demos-launcher.js"
)

includeFilter in JekyllSupport.Jekyll ~= { _ || "*.svg" }

makeSite := {
  (fullOptJS in Compile in demos).value
  makeSite.value
}

GhPagesKeys.pushSite := {
  if (sys.env.getOrElse("TRAVIS_PULL_REQUEST", "") == "false") {
    GhPagesKeys.pushSite.value
  }
}