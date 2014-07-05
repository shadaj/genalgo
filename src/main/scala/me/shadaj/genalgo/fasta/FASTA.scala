package me.shadaj.genalgo.fasta

import me.shadaj.genalgo.sequences.BaseLike
import me.shadaj.genalgo.sequences.DNA
import me.shadaj.genalgo.sequences.DNABase
import me.shadaj.genalgo.sequences.BioSequence
import me.shadaj.genalgo.sequences.RNABase
import me.shadaj.genalgo.sequences.RNA
import me.shadaj.genalgo.sequences.AminoAcid
import me.shadaj.genalgo.sequences.Protein

case class FASTA[B <: BaseLike, C <: BioSequence[B, _]](id: String, sequence: C)

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