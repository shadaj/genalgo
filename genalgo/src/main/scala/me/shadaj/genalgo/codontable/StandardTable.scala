package me.shadaj.genalgo.codontable

import scala.io.Source
import me.shadaj.genalgo.sequences.RNA
import me.shadaj.genalgo.sequences.AminoAcid
import me.shadaj.genalgo.sequences.Protein
import me.shadaj.genalgo.sequences.DNA
import me.shadaj.genalgo.sequences.DNABase

object StandardTable extends CodonTable {
  private val source = Source.fromInputStream(getClass().getResourceAsStream("/codonTables/standard.txt"))

  private val lines = source.getLines.toIndexedSeq
  
  private val table = lines.flatMap { l =>
    l.split('\t').map { p =>
      val split = p.split(" ")
      DNA(split(0).map(DNABase.fromChar)).toRNA -> AminoAcid.fromChar(split(1).head)
    }
  }.toMap
  
  def aminoForCodon(codon: RNA) = {
    if (codon.length != 3) {
      throw new IllegalArgumentException("A codon's length must be 3")
    }
    table(codon)
  }
  
  def proteinForRNA(seq: RNA) = {
    Protein(seq.grouped(3).map(aminoForCodon).toIndexedSeq)
  }
  
  def codonsForAmino(acid: AminoAcid) = {
    table.filter(_._2 == acid).map(_._1).toList
  }
}