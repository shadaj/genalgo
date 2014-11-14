package me.shadaj.genalgo.tests

import me.shadaj.genalgo.proteins.codontable.StandardTable
import utest._

import me.shadaj.genalgo.sequences.RNA
import me.shadaj.genalgo.sequences.U
import me.shadaj.genalgo.sequences.Phe
import me.shadaj.genalgo.sequences.C
import me.shadaj.genalgo.sequences.Protein
import me.shadaj.genalgo.sequences.Ser

object CodonTableTest extends TestSuite {
  val tests = TestSuite {
    "Standard/Codon" - {
      assert(StandardTable.aminoForCodon(RNA(U,U,U)) == Phe)
    }

    "Standard/RNA" - {
      assert(StandardTable.proteinForRNA(RNA(U, U, U, U, C, U)) == Protein(Phe, Ser))
    }
  }
}
