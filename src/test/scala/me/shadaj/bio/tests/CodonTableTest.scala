package me.shadaj.bio.tests

import org.scalatest.FunSuite
import me.shadaj.bio.uniprot.Uniprot
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration._
import me.shadaj.bio.codontable.StandardTable
import me.shadaj.bio.sequences.RNA
import me.shadaj.bio.sequences.U
import me.shadaj.bio.sequences.Phe
import me.shadaj.bio.sequences.C
import me.shadaj.bio.sequences.Protein
import me.shadaj.bio.sequences.Ser

class CodonTableTest extends FunSuite {
  test("Standard/Codon") {
    assert(StandardTable.aminoForCodon(RNA.fromSeq(IndexedSeq(U, U, U))) === Phe)
  }
  
  test("Standard/RNA") {
    assert(StandardTable.proteinForRNA(RNA.fromSeq(IndexedSeq(U, U, U, U, C, U))) === Protein.fromSeq(IndexedSeq(Phe, Ser)))
  }
}