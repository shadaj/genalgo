package me.shadaj.bio.uniprot

import java.io.FileNotFoundException
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import me.shadaj.bio.fasta._
import me.shadaj.bio.sequences.AminoAcid
import me.shadaj.bio.sequences.Protein

object Uniprot {
  def getFasta(id: String): Future[FASTA[AminoAcid, Protein]] = {
    Future(Source.fromURL(s"http://www.uniprot.org/uniprot/$id.fasta")).map { source =>
      FASTAParser.parseProtein(source.getLines.mkString("\n")).head
    }.recoverWith {
      case _: FileNotFoundException => throw new IllegalArgumentException("Invalid ID")
    }
  }
}