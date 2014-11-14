package me.shadaj.genalgo.network

import org.scalajs.dom.extensions.Ajax

import scala.concurrent.ExecutionContext

object Requester extends RequestMaker {
  def get(url: String)(implicit context: ExecutionContext) = Ajax.get(url).map(_.responseText)

  def post(url:String)(implicit context: ExecutionContext) = Ajax.post(url)
}
