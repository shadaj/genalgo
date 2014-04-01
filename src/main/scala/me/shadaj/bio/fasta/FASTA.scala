package me.shadaj.bio.fasta

import me.shadaj.bio.sequences.BaseLike
import me.shadaj.bio.sequences.DNA
import me.shadaj.bio.sequences.DNABase
import me.shadaj.bio.sequences.Sequence
import me.shadaj.bio.sequences.RNABase
import me.shadaj.bio.sequences.RNA
import me.shadaj.bio.sequences.AminoAcid
import me.shadaj.bio.sequences.Protein

case class FASTA[B <: BaseLike, C <: Sequence[B, _]](id: String, sequence: C)

object FASTAParser {
  def parseDNA(data: String): List[FASTA[DNABase, DNA]] = {
    val unparsedFatsas = data.split(">")
    unparsedFatsas.tail.map { s =>
      val linesplit = s.split('\n')
      FASTA[DNABase, DNA](linesplit(0),
    		DNA(linesplit.tail.mkString))
    }.toList
  }
  
  def parseRNA(data: String): List[FASTA[RNABase, RNA]] = {
    val unparsedFatsas = data.split(">")
    unparsedFatsas.tail.map { s =>
      val linesplit = s.split('\n')
      FASTA[RNABase, RNA](linesplit(0),
        RNA(linesplit.tail.mkString))
    }.toList
  }
  
  def parseProtein(data: String): List[FASTA[AminoAcid, Protein]] = {
    val unparsedFatsas = data.split(">")
    unparsedFatsas.tail.map { s =>
      val linesplit = s.split('\n')
      FASTA[AminoAcid, Protein](linesplit(0),
        Protein(linesplit.tail.mkString))
    }.toList
  }
}