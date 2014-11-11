package me.shadaj.genalgo.tests

import utest._

import me.shadaj.genalgo.alignment.GlobalAlignment
import me.shadaj.genalgo.alignment.LocalAlignment
import me.shadaj.genalgo.scoring.BLOSUM62
import me.shadaj.genalgo.scoring.PAM250
import me.shadaj.genalgo.sequences.Protein

object GlobalAlignmentTest extends TestSuite {
  implicit val strategy = GlobalAlignment

  val tests = TestSuite {
    "empty sequences should have alignment score of 0" - {
      val alignment = Protein("").align(Protein(""), new BLOSUM62(5))
      assert(alignment.score == 0)
    }

    "matching sequences" - {
      val alignment = Protein("A").align(Protein("A"), new BLOSUM62(5))
      assert(alignment.score == 4)
    }

    "one empty protein" - {
      assert(Protein("A").align(Protein(""), new BLOSUM62(5)).score == -5)
      assert(Protein("").align(Protein("A"), new BLOSUM62(5)).score == -5)
    }

    // https://stepic.org/lesson/From-Global-to-Local-Alignment-247/step/3
    "Stepic Small Dataset" - {
      val alignment = Protein("PLEASANTLY").align(Protein("MEANLY"), new BLOSUM62(5))
      assert(alignment.score == 8)
    }

    // https://stepic.org/media/attachments/lessons/247/global_alignment.txt
    "Stepic Extra Dataset" - {
      val proteins = Resources.global_alignment_test_proteins
      val p1 = Protein(proteins(0))
      val p2 = Protein(proteins(1))
      val alignment = p1.align(p2, new BLOSUM62(5))
      assert(alignment.score == 1555)
    }
  }
}

object LocalAlignmentTest extends TestSuite {
  implicit val strategy = LocalAlignment
  val tests = TestSuite {
    "empty sequences should have alignment score of 0" - {
      val alignment = Protein("").align(Protein(""), new BLOSUM62(5))
      assert(alignment.score == 0)
    }

    "matching sequences" - {
      val alignment = Protein("A").align(Protein("A"), new PAM250(5))
      assert(alignment.score == 2)
    }

    // https://stepic.org/lesson/From-Global-to-Local-Alignment-247/step/9
    "Stepic Small Dataset" - {
      val alignment = Protein("MEANLY").align(Protein("PENALTY"), new PAM250(5))
      assert(alignment.score == 15)
    }

    // https://stepic.org/media/attachments/lessons/247/local_alignment.txt
    "Stepic Extra Dataset" - {
      val proteins = Resources.local_alignment_test_proteins
      val p1 = Protein(proteins(0))
      val p2 = Protein(proteins(1))
      val alignment = p1.align(p2, new PAM250(5))
      assert(alignment.score == 1062)
    }
  }
}
