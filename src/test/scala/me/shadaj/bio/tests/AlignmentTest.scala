package me.shadaj.bio.tests

import org.scalatest.FunSuite
import me.shadaj.bio.alignment.GlobalAlignment
import me.shadaj.bio.sequences._
import me.shadaj.bio.scoring.BLOSUM62
import org.scalatest.FunSuite

class GlobalAlignmentTest extends FunSuite {
  implicit val strategy = GlobalAlignment
  
  test("same sequences should have alignment score of 0") {
    val alignment = "ATGC".asProtein.align("ATGC".asProtein, new BLOSUM62(5))
  }
  
  test("1") {
    val alignment = "PLEASANTLY".asProtein.align("MEANLY".asProtein, new BLOSUM62(5))
    assert(alignment.score === 8)
  }
}