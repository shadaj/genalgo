package me.shadaj.genalgo.tests

import utest._

import scala.io.Source
import me.shadaj.genalgo.reconstruction.ConvolutionLeaderboardSequencing

object CyclopeptideSequencingTest extends TestSuite {
  val tests = TestSuite {
    "Stepic Small Dataset" - {
      val spectrum = "57 57 71 99 129 137 170 186 194 208 228 265 285 299 307 323 356 364 394 422 493".split(' ').map(_.toInt).toList
      assert(new ConvolutionLeaderboardSequencing(20, 60).sequence(spectrum) == "57-129-99-71-66-71".split('-').map(_.toInt).toList)
    }

    "Stepic Large Dataset" - {
      val spectrum = TestResources.cyclopeptide_sequencing_test_spectrum.head.split(' ').map(_.toInt)
      assert(new ConvolutionLeaderboardSequencing(16, 84).sequence(spectrum) == "147-99-114-57-129-87-71-137-87-101-129-147-57".split('-').map(_.toInt).toList)
    }
  }
}
