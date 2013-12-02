package me.shadaj.bio.uniprot

import java.io.FileNotFoundException

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source

import me.shadaj.bio.fasta._
import me.shadaj.bio.util.Util.retry

object Uniprot {
  def getFasta(id: String): FASTA = {
    val source = Future(Source.fromURL(s"http://www.uniprot.org/uniprot/$id.fasta"))
    
    try {
      FASTAParser.parse(retry(5, source).getLines.mkString("\n")).head
    } catch {
      case _: FileNotFoundException => throw new IllegalArgumentException("Invalid ID")
    }
  }
}