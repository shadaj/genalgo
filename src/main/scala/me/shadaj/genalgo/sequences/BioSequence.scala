package me.shadaj.genalgo.sequences

import scala.collection.IndexedSeqLike
import scala.collection.mutable.Builder
import me.shadaj.genalgo.alignment.AlignmentStrategy
import me.shadaj.genalgo.scoring.ScoringMatrix

trait BioSequence[B <: BaseLike, C <: BioSequence[B, _]] extends IndexedSeq[B] with IndexedSeqLike[B, C] {
  def seqBuilder: Builder[B, C]
  final override protected[this] def newBuilder: Builder[B, C] = seqBuilder
    
  def hammingDistance(that: C) = {
    zip(that).count { case (a, b) => a != b}
  }
  
  def align(that: C, scorer: ScoringMatrix[B])(implicit strategy: AlignmentStrategy) = {
    strategy.align(this, that, scorer)
  }
  
  override def toString = mkString
}