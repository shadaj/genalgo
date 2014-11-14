package me.shadaj.genalgo.services

import me.shadaj.genalgo.network.Requester
import me.shadaj.genalgo.parsers._
import me.shadaj.genalgo.sequences._

import scala.concurrent.{ExecutionContext, Future}

object Uniprot {
  def getFasta(id: String)(implicit context: ExecutionContext): Future[FASTA[AminoAcid, Protein]] = {
    Requester.get(s"http://www.uniprot.org/uniprot/$id.fasta").map(FASTAParser.parseProtein(_).head)
  }
}