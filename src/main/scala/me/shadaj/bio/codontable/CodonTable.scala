package me.shadaj.bio.codontable

import me.shadaj.bio.sequences.AminoAcid
import me.shadaj.bio.sequences.RNA
import me.shadaj.bio.sequences.Protein

trait CodonTable {
  def aminoForCodon(codon: RNA): AminoAcid
  def proteinForRNA(seq: RNA): Protein
  def codonsForAmino(amino: AminoAcid): List[RNA]
}