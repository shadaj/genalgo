package me.shadaj.genalgo.sequences

import me.shadaj.genalgo.alignment.AlignmentStrategy
import me.shadaj.genalgo.scoring.ScoringMatrix

import scala.collection.{IndexedSeqLike, mutable}

trait BioSequence[B <: BaseLike] extends IndexedSeq[B] { self: IndexedSeqLike[B, _] =>
  type C <: BioSequence[B]

  def seqBuilder: mutable.Builder[B, C]
  override final def newBuilder: mutable.Builder[B, C] = seqBuilder

  def hammingDistance(that: C) = {
    zip(that).count { case (a, b) => a != b}
  }
  
  def align(that: C, scorer: ScoringMatrix[B])(implicit strategy: AlignmentStrategy) = {
    strategy.align(this, that, scorer)
  }

  override def drop(n: Int): C = super.drop(n).asInstanceOf[C]

  override def dropRight(n: Int): C = super.dropRight(n).asInstanceOf[C]
  
  override def toString = mkString
}