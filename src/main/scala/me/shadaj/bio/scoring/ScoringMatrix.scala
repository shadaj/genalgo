package me.shadaj.bio.scoring

import me.shadaj.bio.sequences.BaseLike

trait ScoringMatrix[B <: BaseLike] {
  def score(b1: B, b2: B): Int
  def indelPenalty: Int
}