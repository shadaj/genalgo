package me.shadaj.bio.tests

import org.scalatest.FunSuite
import org.scalacheck.Prop.forAll
import org.scalacheck.Arbitrary
import org.scalacheck.Gen.oneOf
import me.shadaj.bio.sequences._
import org.scalatest.prop.Checkers

class RNATest extends FunSuite with Checkers {
  implicit val arbitraryDNABase: Arbitrary[RNABase] = Arbitrary(oneOf(A, G, C, U))
  implicit val arbitraryRNA = Arbitrary(Arbitrary.arbitrary[IndexedSeq[RNABase]].map(RNA.apply))

  test("A complement leads to correct bases") {
    val complementMap: Map[RNABase, RNABase] = Map(A -> U, U -> A, G -> C, C -> G)
    check {
      forAll { (rna: RNA) =>
        val complement = rna.complement
        rna.zip(complement).forall { case (o, c) =>
          complementMap(o) == c
        }
      }
    }
  }

  test("A complement of a complement is the original sequence") {
    check {
      forAll { (rna: RNA) =>
        rna.complement.complement == rna
      }
    }
  }
}
