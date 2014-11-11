package me.shadaj.genalgo.tests

import utest._

import org.scalacheck.Prop.forAll
import org.scalacheck.Arbitrary
import org.scalacheck.Gen.oneOf
import me.shadaj.genalgo.sequences._

object DNATest extends TestSuite {
  implicit val arbitraryDNABase: Arbitrary[DNABase] = Arbitrary(oneOf(A, G, C, T))
  implicit val arbitraryDNA = Arbitrary(Arbitrary.arbitrary[IndexedSeq[DNABase]].map(DNA.apply))

  val tests = TestSuite {
    "A complement leads to correct bases" - {
      val complementMap: Map[DNABase, DNABase] = Map(A -> T, T -> A, G -> C, C -> G)
      forAll { (dna: DNA) =>
        val complement = dna.complement
        dna.zip(complement).forall { case (o, c) =>
          complementMap(o) == c
        }
      }.check
    }

    "A complement of a complement is the original sequence" - {
      forAll { (dna: DNA) =>
        dna.complement.complement == dna
      }.check
    }
  }
}
