package me.shadaj.genalgo.network

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

object Requester extends RequestMaker {
  def get(url: String)(implicit context: ExecutionContext) = Future(Source.fromURL(url)).map(_.getLines.mkString("\n"))
}
