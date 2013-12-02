package me.shadaj.bio.proteins

import scala.io.Source

object ProteinWeights {
  private val tableLines = Source.fromInputStream(
      ProteinWeights.getClass().getResourceAsStream("/protein_mass_table.txt")
  ).getLines
  
  val table: Map[String, Int] = tableLines.map { s =>
    val split = s.split(' ')
    split(0) -> split(1).toInt
  }.toMap
  
  val proteinTypes: List[String] = table.map(_._1).toList
  
  def weightOf(peptide: Char): Int = table(peptide.toString)
  
  def weightOf(peptide: String): Int = peptide.map(weightOf).sum
}