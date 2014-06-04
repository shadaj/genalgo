package me.shadaj.bio.alignment

import me.shadaj.bio.sequences.BioSequence
import me.shadaj.bio.scoring.ScoringMatrix
import me.shadaj.bio.sequences.BaseLike

trait AlignmentStrategy {
  def align[B <: BaseLike, C <: BioSequence[B, _]](seq1: C, seq2: C, scorer: ScoringMatrix[B]): Alignment
}