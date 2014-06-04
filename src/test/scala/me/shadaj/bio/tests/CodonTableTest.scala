package me.shadaj.bio.tests

import org.scalatest.FunSuite
import me.shadaj.bio.codontable.StandardTable
import me.shadaj.bio.sequences.RNA
import me.shadaj.bio.sequences.U
import me.shadaj.bio.sequences.Phe
import me.shadaj.bio.sequences.C
import me.shadaj.bio.sequences.Protein
import me.shadaj.bio.sequences.Ser

class CodonTableTest extends FunSuite {
  test("Standard/Codon") {
    assert(StandardTable.aminoForCodon(RNA(U,U,U)) === Phe)
  }
  
  test("Standard/RNA") {
    assert(StandardTable.proteinForRNA(RNA(U, U, U, U, C, U)) === Protein(Phe, Ser))
  }
}