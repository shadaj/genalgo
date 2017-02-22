package me.shadaj.genalgo.tests

import utest._

import me.shadaj.genalgo.uniprot.Uniprot
import utest.framework.ExecutionContext.RunNow

object UniprotTest extends TestSuite {
  val tests = TestSuite {
    "can retrieve proper data" - {
      Uniprot.getFasta("P20840").map(f => assert(f.sequence.toString == Resources.uniprot_test_protein.head))
    }

    "proper exception for invalid data" - {
      Uniprot.getFasta("P").map(_ => assert(false)).recover {
        case _ => assert(true)
      }
    }
  }
}
