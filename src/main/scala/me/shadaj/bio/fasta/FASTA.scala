package me.shadaj.bio.fasta

import me.shadaj.bio.sequences.Sequence

case class FASTA(id: String, sequence: Sequence)

object FASTAParser {
  def parse(data: String): List[FASTA] = {
    val unparsedFatsas = data.split(">")
    unparsedFatsas.tail.map { s =>
      val linesplit = s.split('\n')
      FASTA(linesplit(0),
    		new Sequence(linesplit.tail.mkString))
    }.toList
  }
}