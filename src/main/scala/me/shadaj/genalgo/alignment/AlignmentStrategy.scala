package me.shadaj.genalgo.alignment

import me.shadaj.genalgo.sequences.BioSequence
import me.shadaj.genalgo.scoring.ScoringMatrix
import me.shadaj.genalgo.sequences.BaseLike

import scala.collection.IndexedSeqLike

trait AlignmentStrategy {
  def align[B <: BaseLike, C <: BioSequence[B] with IndexedSeqLike[B, C]](seq1: C, seq2: C, scorer: ScoringMatrix[B]): Alignment[B, C]
}