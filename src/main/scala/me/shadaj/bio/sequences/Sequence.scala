package me.shadaj.bio.sequences

import scala.collection.IndexedSeqLike
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder
import me.shadaj.bio.alignment.AlignmentStrategy
import me.shadaj.bio.scoring.ScoringMatrix

trait Sequence[B <: BaseLike, C <: Sequence[B, _]] extends IndexedSeq[B] with IndexedSeqLike[B, C] {
  
  override protected[this] def newBuilder: Builder[B, C] = ???
    
  def hammingDistance(that: C) = {
    zip(that).count { case (a, b) => a != b}
  }
  
  def align(that: C, scorer: ScoringMatrix[B])(implicit strategy: AlignmentStrategy) = {
    strategy.align(this, that, scorer)
  } 
  
  override def toString = mkString
}