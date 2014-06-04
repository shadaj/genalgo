package me.shadaj.bio.tests

import me.shadaj.bio.uniprot.Uniprot
import me.shadaj.bio.scoring.BLOSUM62
import me.shadaj.bio.alignment.GlobalAlignment
import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  implicit val alignmentStrategy = GlobalAlignment
  val alignmentFuture = Uniprot.getFasta("A2AAJ9").zip(Uniprot.getFasta("Q5VST9")).map { case (mouse, human) =>
    val alignment = mouse.sequence.align(human.sequence, new BLOSUM62(5))
    println(alignment)
    alignment.toImageFile(new File("obscurin.png"))
  }

  Await.result(alignmentFuture, Duration.Inf)
}
