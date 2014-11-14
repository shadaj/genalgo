package me.shadaj.genalgo.parsers

import me.shadaj.genalgo.sequences.{AminoAcid, BaseLike, BioSequence, DNA, DNABase, Protein, RNA, RNABase}

case class FASTA[B <: BaseLike, C <: BioSequence[B]](id: String, sequence: C)

object FASTAParser {


  def parseDNA(data: String): Stream[FASTA[DNABase, DNA]] = {
    val unparsedFatsas = data.split(">")
    unparsedFatsas.tail.map { s =>
      val linesplit = s.split('\n')
      FASTA[DNABase, DNA](linesplit(0), DNA(linesplit.tail.mkString))
    }.toStream
  }
  
  def parseRNA(data: String): Stream[FASTA[RNABase, RNA]] = {
    val unparsedFatsas = data.split(">")
    unparsedFatsas.tail.map { s =>
      val linesplit = s.split('\n')
      FASTA[RNABase, RNA](linesplit(0),
        RNA(linesplit.tail.mkString))
    }.toStream
  }
  
  def parseProtein(data: String): Stream[FASTA[AminoAcid, Protein]] = {
    val unparsedFatsas = data.split(">")
    unparsedFatsas.tail.map { s =>
      val linesplit = s.split('\n')
      FASTA[AminoAcid, Protein](linesplit(0),
        Protein(linesplit.tail.mkString))
    }.toStream
  }
}