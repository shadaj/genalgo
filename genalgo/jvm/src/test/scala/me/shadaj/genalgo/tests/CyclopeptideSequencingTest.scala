package me.shadaj.genalgo.tests

import org.scalatest.FunSuite
import scala.io.Source
import me.shadaj.genalgo.reconstruction.ConvolutionLeaderboardSequencing

class CyclopeptideSequencingTest extends FunSuite {
  test("Stepic Small Dataset") {
    val spectrum = "57 57 71 99 129 137 170 186 194 208 228 265 285 299 307 323 356 364 394 422 493".split(' ').map(_.toInt).toList
    assert(new ConvolutionLeaderboardSequencing(20, 60).sequence(spectrum) == "57-129-99-71-66-71".split('-').map(_.toInt).toList)
  }

  test("Stepic Large Dataset") {
    val spectrum = Source.fromInputStream(getClass().getResourceAsStream("/cyclopeptide_sequencing_test_spectrum.txt")).getLines.next.split(' ').map(_.toInt)
    assert(new ConvolutionLeaderboardSequencing(16, 84).sequence(spectrum) == "147-99-114-57-129-87-71-137-87-101-129-147-57".split('-').map(_.toInt).toList)
  }
}