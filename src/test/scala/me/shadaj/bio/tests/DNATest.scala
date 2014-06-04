package me.shadaj.bio.tests

import org.scalatest.FunSuite
import org.scalacheck.Prop.forAll
import org.scalacheck.Arbitrary
import org.scalacheck.Gen.oneOf
import me.shadaj.bio.sequences._
import org.scalatest.prop.Checkers

class DNATest extends FunSuite with Checkers {
  implicit val arbitraryDNABase: Arbitrary[DNABase] = Arbitrary(oneOf(A, G, C, T))
  implicit val arbitraryDNA = Arbitrary(Arbitrary.arbitrary[IndexedSeq[DNABase]].map(DNA.apply))

  test("A complement leads to correct bases") {
    val complementMap: Map[DNABase, DNABase] = Map(A -> T, T -> A, G -> C, C -> G)
    check {
      forAll { (dna: DNA) =>
        val complement = dna.complement
        dna.zip(complement).forall { case (o, c) =>
          complementMap(o) == c
        }
      }
    }
  }

  test("A complement of a complement is the original sequence") {
    check {
      forAll { (dna: DNA) =>
        dna.complement.complement == dna
      }
    }
  }
}
