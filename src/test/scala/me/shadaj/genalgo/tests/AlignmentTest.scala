package me.shadaj.genalgo.tests

import scala.io.Source

import org.scalatest.FunSuite

import me.shadaj.genalgo.alignment.GlobalAlignment
import me.shadaj.genalgo.alignment.LocalAlignment
import me.shadaj.genalgo.scoring.BLOSUM62
import me.shadaj.genalgo.scoring.PAM250
import me.shadaj.genalgo.sequences.Protein

class GlobalAlignmentTest extends FunSuite {
  implicit val strategy = GlobalAlignment

  test("empty sequences should have alignment score of 0") {
    val alignment = Protein("").align(Protein(""), new BLOSUM62(5))
    assert(alignment.score === 0)
  }
  
  test("matching sequences") {
    val alignment = Protein("A").align(Protein("A"), new BLOSUM62(5))
    assert(alignment.score === 4)
  }
  
  test("one empty protein") {
    assert(Protein("A").align(Protein(""), new BLOSUM62(5)).score == -5)
    assert(Protein("").align(Protein("A"), new BLOSUM62(5)).score == -5)
  }
  
  // https://stepic.org/lesson/From-Global-to-Local-Alignment-247/step/3
  test("Stepic Small Dataset") {
    val alignment = Protein("PLEASANTLY").align(Protein("MEANLY"), new BLOSUM62(5))
    assert(alignment.score === 8)
  }
  
  // https://stepic.org/media/attachments/lessons/247/global_alignment.txt
  test("Stepic Extra Dataset") {
    val proteins = Source.fromInputStream(getClass().getResourceAsStream("/global_alignment_test_proteins.txt")).getLines
    val p1 = Protein(proteins.next)
    val p2 = Protein(proteins.next)
    val alignment = p1.align(p2, new BLOSUM62(5))
    assert(alignment.score === 1555)
  }
}

class LocalAlignmentTest extends FunSuite {
  implicit val strategy = LocalAlignment

  test("empty sequences should have alignment score of 0") {
    val alignment = Protein("").align(Protein(""), new BLOSUM62(5))
    assert(alignment.score === 0)
  }
  
  test("matching sequences") {
    val alignment = Protein("A").align(Protein("A"), new PAM250(5))
    assert(alignment.score === 2)
  }
  
  // https://stepic.org/lesson/From-Global-to-Local-Alignment-247/step/9
  test("Stepic Small Dataset") {
    val alignment = Protein("MEANLY").align(Protein("PENALTY"), new PAM250(5))
    assert(alignment.score === 15)
  }
  
  // https://stepic.org/media/attachments/lessons/247/local_alignment.txt
  test("Stepic Extra Dataset") {
    val proteins = Source.fromInputStream(getClass().getResourceAsStream("/local_alignment_test_proteins.txt")).getLines
    val p1 = Protein(proteins.next)
    val p2 = Protein(proteins.next)
    val alignment = p1.align(p2, new PAM250(5))
    assert(alignment.score === 1062)
  }
}