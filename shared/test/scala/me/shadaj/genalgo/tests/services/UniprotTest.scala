package me.shadaj.genalgo.tests.services

import me.shadaj.genalgo.tests.TestResources
import me.shadaj.genalgo.services.Uniprot
import utest._
import utest.ExecutionContext.RunNow

object UniprotTest extends TestSuite {
  val tests = TestSuite {
    "can retrieve proper data" - {
      Uniprot.getFasta("P20840").map(f => assert(f.sequence.toString == TestResources.uniprot_test_protein.head))
    }

    "proper exception for invalid data" - {
      Uniprot.getFasta("P").map(_ => assert(false)).recover {
        case _ => assert(true)
      }
    }
  }
}