package me.shadaj.genalgo.pv

import me.shadaj.genalgo.pv.mol.Mol

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("pv.io")
object IO extends js.Object {
  def pdb(pdbData: String): Mol = js.native
  def sdf(sdfData: String): Mol = js.native
}
