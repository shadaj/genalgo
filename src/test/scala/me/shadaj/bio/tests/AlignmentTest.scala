package me.shadaj.bio.tests

import scala.io.Source

import org.scalatest.FunSuite

import me.shadaj.bio.alignment.GlobalAlignment
import me.shadaj.bio.alignment.LocalAlignment
import me.shadaj.bio.scoring.BLOSUM62
import me.shadaj.bio.scoring.PAM250
import me.shadaj.bio.sequences.SequenceString

class GlobalAlignmentTest extends FunSuite {
  implicit val strategy = GlobalAlignment
  
  test("empty sequences should have alignment score of 0") {
    val alignment = "".asProtein.align("".asProtein, new BLOSUM62(5))
    assert(alignment.score === 0)
  }
  
  test("matching sequences") {
    val alignment = "A".asProtein.align("A".asProtein, new BLOSUM62(5))
    assert(alignment.score === 4)
  }
  
  test("one empty protein") {
    assert("A".asProtein.align("".asProtein, new BLOSUM62(5)).score == -5)
    assert("".asProtein.align("A".asProtein, new BLOSUM62(5)).score == -5)
  }
  
  // https://stepic.org/lesson/From-Global-to-Local-Alignment-247/step/3
  test("Stepic Small Dataset") {
    val alignment = "PLEASANTLY".asProtein.align("MEANLY".asProtein, new BLOSUM62(5))
    assert(alignment.score === 8)
  }
  
  // https://stepic.org/media/attachments/lessons/247/global_alignment.txt
  test("Stepic Extra Dataset") {
    val proteins = Source.fromInputStream(getClass().getResourceAsStream("/global_alignment_test_proteins.txt")).getLines
    val p1 = proteins.next.asProtein
    val p2 = proteins.next.asProtein
    val alignment = p1.align(p2, new BLOSUM62(5))
    assert(alignment.score === 1555)
  }
}

class LocalAlignmentTest extends FunSuite {
  implicit val strategy = LocalAlignment
  
  test("empty sequences should have alignment score of 0") {
    val alignment = "".asProtein.align("".asProtein, new BLOSUM62(5))
    assert(alignment.score === 0)
  }
  
  test("matching sequences") {
    val alignment = "A".asProtein.align("A".asProtein, new PAM250(5))
    assert(alignment.score === 2)
  }
  
  // https://stepic.org/lesson/From-Global-to-Local-Alignment-247/step/9
  test("Stepic Small Dataset") {
    val alignment = "MEANLY".asProtein.align("PENALTY".asProtein, new PAM250(5))
    assert(alignment.score === 15)
  }
  
  // https://stepic.org/media/attachments/lessons/247/local_alignment.txt
  test("Stepic Large Dataset") {
    val proteins = Source.fromInputStream(getClass().getResourceAsStream("/local_alignment_test_proteins.txt")).getLines
    val p1 = proteins.next.asProtein
    val p2 = proteins.next.asProtein
    val alignment = p1.align(p2, new PAM250(5))
    assert(alignment.score === 1062)
  }
}