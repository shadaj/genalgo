package me.shadaj.genalgo.tests

import utest._
import me.shadaj.genalgo.alignment.AlignmentSequence
import me.shadaj.genalgo.sequences._

object AlignmentSequenceTest extends TestSuite {
  val tests = TestSuite {
    "able to retrieve data (sorted indels)" - {
      val alignment = new AlignmentSequence(DNA("ATT"), Array(1, 4))
      assert(alignment.toString == "A-TT-")
    }

    "able to retrieve data (unsorted indels)" - {
      val alignment = new AlignmentSequence(DNA("ATT"), Array(4, 1))
      assert(alignment.toString == "A-TT-")
    }
  }
}
