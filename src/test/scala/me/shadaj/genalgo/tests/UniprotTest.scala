package me.shadaj.genalgo.tests

import org.scalatest.FunSuite
import me.shadaj.genalgo.uniprot.Uniprot
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration._

class UniprotTest extends FunSuite {
  test("can retrieve proper data") {
    assert(Await.result(Uniprot.getFasta("P20840"), 2 second).sequence.toString ===
      Source.fromInputStream(getClass().getResourceAsStream("/uniprot_test_protein.txt")).getLines.next)
  }

  test("proper exception for invalid data") {
    Uniprot.getFasta("P").onComplete {
      case Failure(_: IllegalArgumentException) => assert(true)
      case other => assert(false)
    }
  }
}