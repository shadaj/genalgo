package me.shadaj.bio.proteins

import scala.io.Source

object AminoAcidTable {
  private val source = Source.fromInputStream(getClass().getResourceAsStream("/amino_acid_table.txt"))

  private val lines = source.getLines.toIndexedSeq
  
  (for (l <- lines; g <- l.split('/')) yield {
    val mapSplit = g.split(' ')
    mapSplit(0) -> mapSplit(1)
  }).toMap
  
  private val table = (for (l <- lines; g <- l.split('/')) yield {
    val mapSplit = g.split(' ')
    mapSplit(0) -> mapSplit(1)
  }).toMap
  
  def aminoAcid(codon: String) = {
    if (codon.length != 3) {
      throw new IllegalArgumentException("A codon's length must be 3")
    }
    table(codon)
  }
  
  def aminoChainForRna(sequence: String) = {
    sequence.grouped(3).map(aminoAcid).toList
  }
  
  def codonsForAminoAcid(acid: String) = {
    table.filter(_._2 == acid).map(_._1).toList
  }
}