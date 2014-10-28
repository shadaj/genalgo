import sbt.Keys._
import sbt._
import bintray.Opts
import bintray.Plugin.bintraySettings
import bintray.Keys._
import com.typesafe.sbt.packager.universal.UniversalKeys
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import com.inthenow.sbt.scalajs.SbtScalajs
import com.inthenow.sbt.scalajs.SbtScalajs._

object Build extends sbt.Build with UniversalKeys {


  lazy val publishSettings = Seq(
    repository in bintray := "denigma-releases",

    bintrayOrganization in bintray := Some("denigma"),

    licenses += ("MPL-2.0", url("http://opensource.org/licenses/MPL-2.0"))
  )

  lazy val sharedSettings = Seq(
    organization := "me.shadaj",
    scalaVersion := "2.11.2",
    version := "0.1.3"
  )

  /**
   * For parts of the project that we will not publish
   */
  lazy val noPublishSettings = Seq(
    publish := (),
    publishLocal := (),
    publishArtifact := false
  )

  lazy val genalgoJsSettings =  bintraySettings++sharedSettings++ linkedSources(shared) ++ scalajsJsSettings ++ publishSettings

  lazy val genalgoJvmSettings = bintraySettings++ sharedSettings++ linkedSources(shared) ++ scalajsJvmSettings ++ publishSettings

  lazy val root = Project(
    id = "root",
    base = file(".")
  ) .settings(sharedSettings:_*)
    .settings(noPublishSettings:_*)
    .dependsOn(genalgo_js, genalgo_jvm)
    .aggregate(genalgo_js, genalgo_jvm)


  /**
   * Shared projects
   */
  lazy val shared = Project(
    id = "genalgo_shared",
    base = file("genalgo")
  ).settings(sharedSettings:_*).settings(noPublishSettings:_*)

  /** `models_js`, a js only meta project. */
  lazy val genalgo_js = Project(
    id = "genalgo_js",
    base = file("genalgo/js")
  )
    .settings(genalgoJsSettings:_*)
    .settings(name := "genalgo")
    .enablePlugins(SbtScalajs)


  lazy val genalgo_jvm = Project(
    id = "genalgo_jvm",
    base = file("genalgo/jvm")
  ).settings(genalgoJvmSettings:_*)
    .settings(
      name := "genalgo",
      libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test",
      libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.11.6" % "test"
    )


}
