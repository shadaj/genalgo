package me.shadaj.genalgo.network

import scala.concurrent.{ExecutionContext, Future}

trait RequestMaker {
  def get(url: String)(implicit context: ExecutionContext): Future[String]
}
