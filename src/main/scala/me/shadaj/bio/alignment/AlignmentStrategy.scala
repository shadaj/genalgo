package me.shadaj.bio.alignment

import me.shadaj.bio.sequences.Sequence
import me.shadaj.bio.scoring.ScoringMatrix
import me.shadaj.bio.sequences.BaseLike

trait AlignmentStrategy {
  def align[B <: BaseLike, C <: Sequence[B, _]](seq1: C, seq2: C, scorer: ScoringMatrix[B]): Alignment
}