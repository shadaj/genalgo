import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys

utest.jsrunner.Plugin.utestJsSettings

ScalaJSKeys.jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM

libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6"