package me.shadaj.bio.proteins

import scala.io.Source
import me.shadaj.bio.sequences.AminoAcid
import me.shadaj.bio.sequences.Protein

object ProteinWeights {
  private val tableLines = Source.fromInputStream(
      ProteinWeights.getClass().getResourceAsStream("/protein_mass_table.txt")
  ).getLines
  
  private val table: Map[AminoAcid, Int] = tableLines.map { s =>
    val split = s.split(' ')
    AminoAcid.fromChar(split(0).head) -> split(1).toInt
  }.toMap
  
  def weightOf(acid: AminoAcid): Int = table(acid)
  
  def weightOf(protein: Protein): Int = protein.map(weightOf).sum
}