import sbt.Keys._
import sbt._
import bintray.Opts
import bintray.Plugin.bintraySettings
import bintray.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import scala.scalajs.sbtplugin.env.phantomjs.PhantomJSEnv

import scala.io.Source

lazy val publishSettings = bintrayPublishSettings ++ Seq(
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)
def sourceForDir(directory: File): String = {
  directory.listFiles().map { file =>
    if (file.isDirectory) {
      s"""object ${file.name} {
             |${sourceForDir(file)}
             |}""".stripMargin
    } else {
      val fileLines = Source.fromFile(file).getLines().toList
      val stringList = fileLines.map(s => '"' + s + '"').toString
      s"""val ${file.name.split('.').head} = $stringList"""
    }
  }.mkString("\n")
}

lazy val generatorSettings = Seq(
  sourceGenerators in Compile <+= baseDirectory map { dir =>
    val fileToWrite = dir / ".." / "shared" / "gen" / "scala" / "me/shadaj/genalgo" / "Resources.scala"
    val folderToRead = dir / ".." / "shared" / "main" / "resources"
    val toWrite =
      s"""package me.shadaj.genalgo
         |object Resources {
         |${sourceForDir(folderToRead)}
         |}""".stripMargin
    IO.write(fileToWrite, toWrite)
    Seq(fileToWrite)
  },
  cleanFiles <+= baseDirectory { base => base / "shared" / "gen" },
  sourceGenerators in Test <+= baseDirectory map { dir =>
    val fileToWrite = dir / ".." / "shared" / "testGen" / "scala" / "me/shadaj/genalgo/tests" / "Resources.scala"
    val folderToRead = dir / ".." / "shared" / "test" / "resources"
    val toWrite =
      s"""package me.shadaj.genalgo.tests
         |object Resources {
         |${sourceForDir(folderToRead)}
         |}""".stripMargin
    IO.write(fileToWrite, toWrite)
    Seq(fileToWrite)
  },
  cleanFiles <+= baseDirectory { base => base / "shared" / "testGen" }
)

lazy val sharedSettings = Seq(
  organization := "me.shadaj",
  name := "genalgo",
  scalaVersion := "2.11.4",
  version := "0.1.4-SNAPSHOT",
  initialCommands := """
                       |import me.shadaj.genalgo
                       |import genalgo._
                     """.stripMargin
)

lazy val genalgoJsSettings =  bintraySettings ++
                              sharedSettings ++
                              generatorSettings ++
                              scalaJSSettings ++
                              publishSettings

lazy val genalgoJvmSettings = bintraySettings ++
                              sharedSettings ++
                              generatorSettings ++
                              publishSettings

lazy val root = project.in(file(".")).aggregate(js, jvm).settings(
  publish := {},
  publishLocal := {}
)

lazy val js = project
  .settings(genalgoJsSettings:_*)
  .settings(
    ScalaJSKeys.preLinkJSEnv := new PhantomJSEnv,
    ScalaJSKeys.postLinkJSEnv := new PhantomJSEnv)
  .settings(
    unmanagedSourceDirectories in Compile +=
      baseDirectory.value / "shared" / "main" / "scala",
    unmanagedSourceDirectories in Test +=
      baseDirectory.value / "shared" / "test" / "scala"
  )

lazy val jvm = project
  .settings(genalgoJvmSettings:_*)
  .settings(
    unmanagedSourceDirectories in Compile +=
      baseDirectory.value / "shared" / "main" / "scala",
    unmanagedSourceDirectories in Test +=
      baseDirectory.value / "shared" / "test" / "scala"
  ).settings(
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % "test"
  )
