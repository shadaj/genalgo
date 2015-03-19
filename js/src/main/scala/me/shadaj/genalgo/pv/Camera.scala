package me.shadaj.genalgo.pv

import scala.scalajs.js

trait Camera extends js.Object {
  def rotateX(deg: Double): Unit = js.native
  def rotateY(deg: Double): Unit = js.native
  def rotateZ(deg: Double): Unit = js.native
}
