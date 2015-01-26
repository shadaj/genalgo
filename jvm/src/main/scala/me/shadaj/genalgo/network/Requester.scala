package me.shadaj.genalgo.network

import scala.concurrent.ExecutionContext
import dispatch._

object Requester extends RequestMaker {
  def get(url: String)(implicit context: ExecutionContext) = Http(dispatch.url(url) OK as.String)
}
