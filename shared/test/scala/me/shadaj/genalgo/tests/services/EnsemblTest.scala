package me.shadaj.genalgo.tests.services

import me.shadaj.genalgo.services.{Ensembl, Uniprot}
import me.shadaj.genalgo.tests.TestResources
import utest.ExecutionContext.RunNow
import utest._

/**
 * Here will be test for Ensembl API
 */
object EnsemblTest {

  val tests = TestSuite {

    "can retrieve proper data" - {
      Ensembl.archive("ENSDART00000075632").map(f => assert(f.elementType.toLowerCase == "transcript"))
    }

  }

}
