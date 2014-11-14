package me.shadaj.genalgo.tests

import me.shadaj.genalgo.scoring.BLOSUM62
import me.shadaj.genalgo.alignment.GlobalAlignment
import java.io.File
import me.shadaj.genalgo.services.Uniprot

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  implicit val strategy = GlobalAlignment

  val alignmentFuture = Uniprot.getFasta("A2AAJ9").zip(Uniprot.getFasta("Q5VST9")).map { case (mouse, human) =>
    val alignment = mouse.sequence.align(human.sequence, new BLOSUM62(5))
    println(alignment)
  }

  Await.result(alignmentFuture, Duration.Inf)
}
