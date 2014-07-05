package me.shadaj.genalgo.uniprot

import java.io.FileNotFoundException
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import me.shadaj.genalgo.fasta._
import me.shadaj.genalgo.sequences.AminoAcid
import me.shadaj.genalgo.sequences.Protein

object Uniprot {
  def getFasta(id: String): Future[FASTA[AminoAcid, Protein]] = {
    Future(Source.fromURL(s"http://www.uniprot.org/uniprot/$id.fasta")).map { source =>
      FASTAParser.parseProtein(source.getLines.mkString("\n")).head
    }.recoverWith {
      case _: FileNotFoundException => throw new IllegalArgumentException("Invalid ID")
    }
  }
}