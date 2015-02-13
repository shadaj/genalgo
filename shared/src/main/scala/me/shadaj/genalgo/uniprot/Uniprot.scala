package me.shadaj.genalgo.uniprot

import me.shadaj.genalgo.network.Requester
import scala.concurrent.{ExecutionContext, Future}
import me.shadaj.genalgo.fasta._
import me.shadaj.genalgo.sequences.AminoAcid
import me.shadaj.genalgo.sequences.Protein

object Uniprot {
  def getFasta(id: String)(implicit context: ExecutionContext): Future[FASTA[AminoAcid, Protein]] = {
    Requester.get(s"http://www.uniprot.org/uniprot/$id.fasta").map(FASTAParser.parseProtein(_).head)
  }
}