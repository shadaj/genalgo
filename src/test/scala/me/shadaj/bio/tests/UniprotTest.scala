package me.shadaj.bio.tests

import org.scalatest.FunSuite
import me.shadaj.bio.uniprot.Uniprot
import scala.io.Source

class UniprotTest extends FunSuite {
  test("can retrieve proper data") {
    val data = Uniprot.getFasta("P20840")
    assert(data.id === "sp|P20840|SAG1_YEAST Alpha-agglutinin OS=Saccharomyces cerevisiae (strain ATCC 204508 / S288c) GN=SAG1 PE=1 SV=2")
    assert(data.sequence.toString() ===  Source.fromInputStream(getClass().getResourceAsStream("/uniprot_test_protein.txt")).getLines.next)
  }

  test("proper exception for invalid data") {
    intercept[IllegalArgumentException](Uniprot.getFasta("P"))
  }
}