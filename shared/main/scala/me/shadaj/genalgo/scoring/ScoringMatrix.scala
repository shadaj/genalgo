package me.shadaj.genalgo.scoring

import me.shadaj.genalgo.sequences.BaseLike

trait ScoringMatrix[B <: BaseLike] extends Serializable {
  def score(b1: B, b2: B): Int
  def indelPenalty: Int
}