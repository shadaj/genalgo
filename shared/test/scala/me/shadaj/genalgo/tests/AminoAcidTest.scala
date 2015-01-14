package me.shadaj.genalgo.tests

import me.shadaj.genalgo.sequences._
import utest._

object AminoAcidTest extends TestSuite {
  val tests = TestSuite {
    "AminoAcid.name gives the correct name" - {
      val names = List(
        Phe -> "Phe",
        Leu -> "Leu",
        Ser -> "Ser",
        Tyr -> "Tyr",
        Cys -> "Cys",
        Trp -> "Trp",
        Pro -> "Pro",
        His -> "His",
        Gln -> "Gln",
        Arg -> "Arg",
        Ile -> "Ile",
        Met -> "Met",
        Thr -> "Thr",
        Asn -> "Asn",
        Lys -> "Lys",
        Val -> "Val",
        Ala -> "Ala",
        Asp -> "Asp",
        Glu -> "Glu",
        Gly -> "Gly",
        Stop -> "Stop"
      )

      names.foreach(t => assert(t._1.name == t._2))
    }
  }
}
