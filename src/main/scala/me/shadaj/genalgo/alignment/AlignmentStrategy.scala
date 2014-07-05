package me.shadaj.genalgo.alignment

import me.shadaj.genalgo.sequences.BioSequence
import me.shadaj.genalgo.scoring.ScoringMatrix
import me.shadaj.genalgo.sequences.BaseLike

trait AlignmentStrategy {
  def align[B <: BaseLike, C <: BioSequence[B, _]](seq1: C, seq2: C, scorer: ScoringMatrix[B]): Alignment
}