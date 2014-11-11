package me.shadaj.genalgo.tests

import utest._

import org.scalacheck.Prop.forAll
import org.scalacheck.Arbitrary
import org.scalacheck.Gen.oneOf
import me.shadaj.genalgo.sequences._

object RNATest extends TestSuite {
  implicit val arbitraryDNABase: Arbitrary[RNABase] = Arbitrary(oneOf(A, G, C, U))
  implicit val arbitraryRNA = Arbitrary(Arbitrary.arbitrary[IndexedSeq[RNABase]].map(RNA.apply))

  val tests = TestSuite {
    "A complement leads to correct bases" - {
      val complementMap: Map[RNABase, RNABase] = Map(A -> U, U -> A, G -> C, C -> G)
      forAll { (rna: RNA) =>
        val complement = rna.complement
        rna.zip(complement).forall { case (o, c) =>
          complementMap(o) == c
        }
      }.check
    }

    "A complement of a complement is the original sequence" - {
      forAll { (rna: RNA) =>
        rna.complement.complement == rna
      }.check
    }
  }
}
