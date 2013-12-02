package me.shadaj.bio.util

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

object Util {
  def retry[T](times: Int, future: Future[T]): T = {
    if (times == 0) {
      Await.result(future, 5 seconds)
    } else {
      try {
        Await.result(future, 5 seconds)
      } catch {
        case _: scala.concurrent.TimeoutException => retry(times - 1, future)
      }
    }
  }
}