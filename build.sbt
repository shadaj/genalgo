import sbt.Keys._
import sbt._
import bintray.Plugin.bintraySettings

import scala.io.Source

import scoverage.ScoverageSbtPlugin.ScoverageKeys.coverage

name := "genalgo-root"

lazy val root = project.in(file(".")).
  aggregate(genalgoJS, genalgoJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val genalgo = crossProject.in(file(".")).
  settings(
    organization := "me.shadaj",
    name := "genalgo",
    scalaVersion := "2.11.5",
    version := "0.1.4-SNAPSHOT",
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0",
    testFrameworks += new TestFramework("utest.runner.Framework"),
    sourceGenerators in Compile <+= baseDirectory map { dir =>
      val fileToWrite = dir / ".." / "shared" / "src" / "gen" / "scala" / "me/shadaj/genalgo" / "Resources.scala"
      val folderToRead = dir / ".." / "shared" / "src" / "main" / "resources"
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
      val toWrite =
        s"""package me.shadaj.genalgo
           |object Resources {
           |${sourceForDir(folderToRead)}
           |}""".stripMargin
      IO.write(fileToWrite, toWrite)
      Seq(fileToWrite)
    },
    cleanFiles <+= baseDirectory { base => base / ".." / "shared" / "src" / "gen" },
    sourceGenerators in Test <+= baseDirectory map { dir =>
      val fileToWrite = dir / ".." / "shared" / "src" / "testGen" / "scala" / "me/shadaj/genalgo/tests" / "Resources.scala"
      val folderToRead = dir / ".." / "shared" / "src" / "test" / "resources"
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
      val toWrite =
        s"""package me.shadaj.genalgo.tests
           |object Resources {
           |${sourceForDir(folderToRead)}
           |}""".stripMargin
      IO.write(fileToWrite, toWrite)
      Seq(fileToWrite)
    },
    cleanFiles <+= baseDirectory { base => base / ".." / "shared" / "src" / "testGen" }
  ).
  settings(bintraySettings ++ bintrayPublishSettings : _*).
  jvmSettings(
    libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
    libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.10" % Test,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % Test
  ).
  jsSettings(
    jsDependencies += RuntimeDOM,
    preLinkJSEnv := PhantomJSEnv().value,
    postLinkJSEnv := PhantomJSEnv().value,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
  )

lazy val genalgoJVM = genalgo.jvm
lazy val genalgoJS = genalgo.js

coverage := {
  coverage.value
  def fileToMkdir = {
    val pathForRoot = (new File("")).toPath.toAbsolutePath.toString
    s"$pathForRoot/jvm/target/scala-2.11/scoverage-report$pathForRoot/jvm"
  }
  (new File(fileToMkdir)).mkdirs
}
