package me.shadaj.genalgo.tests

import me.shadaj.genalgo.alignment.AlignmentSequence
import me.shadaj.genalgo.sequences._
import org.scalatest.FunSuite

class AlignmentSequenceTest extends FunSuite{
  test("Able to retrieve data (sorted indels)") {
    val alignment = new AlignmentSequence(DNA("ATT"), Array(1, 4))
    assert(alignment.toString == "A-TT-")
  }

  test("Able to retrieve data (unsorted indels)") {
    val alignment = new AlignmentSequence(DNA("ATT"), Array(4, 1))
    assert(alignment.toString == "A-TT-")
  }
}
