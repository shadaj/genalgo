package me.shadaj.genalgo.pv

import me.shadaj.genalgo.pv.mol.Mol
import org.scalajs.dom.raw.Element

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

trait Viewer extends js.Object {
  def cartoon(name: String, structure: Mol, options: js.Dynamic): Unit = js.native
  def centerOn(structure: Mol): Unit = js.native
  def autoZoom(): Unit = js.native
  def _cam: Camera = js.native
}

@JSName("pv.Viewer")
object Viewer extends js.Object {
  def apply(parentElement: Element, options: js.Dynamic): Viewer = js.native
}