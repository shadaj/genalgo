package me.shadaj.genalgo.sequences

import me.shadaj.genalgo.alignment.AlignmentStrategy
import me.shadaj.genalgo.scoring.ScoringMatrix

import scala.collection.{IndexedSeqLike, mutable}

trait BioSequence[B <: BaseLike] extends IndexedSeq[B] with Serializable {
  type C <: BioSequence[B] with IndexedSeqLike[B, C]

  def self: C
  def seqBuilder: mutable.Builder[B, C]
  override final def newBuilder: mutable.Builder[B, C] = seqBuilder

  def hammingDistance(that: C) = {
    zip(that).count { case (a, b) => a != b}
  }

  def align(that: C, scorer: ScoringMatrix[B])(implicit strategy: AlignmentStrategy) = {
    strategy.align(self, that, scorer)
  }

  override def toString() = mkString
}
