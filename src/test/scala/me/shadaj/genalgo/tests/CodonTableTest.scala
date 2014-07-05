package me.shadaj.genalgo.tests

import org.scalatest.FunSuite
import me.shadaj.genalgo.codontable.StandardTable
import me.shadaj.genalgo.sequences.RNA
import me.shadaj.genalgo.sequences.U
import me.shadaj.genalgo.sequences.Phe
import me.shadaj.genalgo.sequences.C
import me.shadaj.genalgo.sequences.Protein
import me.shadaj.genalgo.sequences.Ser

class CodonTableTest extends FunSuite {
  test("Standard/Codon") {
    assert(StandardTable.aminoForCodon(RNA(U,U,U)) === Phe)
  }
  
  test("Standard/RNA") {
    assert(StandardTable.proteinForRNA(RNA(U, U, U, U, C, U)) === Protein(Phe, Ser))
  }
}