package me.shadaj.bio.sequences

import scala.collection.IndexedSeqLike
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder

class Sequence(innerSequence: String) extends IndexedSeq[Base] with IndexedSeqLike[Base, Sequence] {
  override protected[this] def newBuilder: Builder[Base, Sequence] = Sequence.newBuilder
  
  def length = innerSequence.length
  def apply(index: Int) = Base.fromChar(innerSequence(index))
  
  def gcContent: Double = {
    val gcs = filter(c => c == G || c == C).length
    (gcs.toDouble/sequence.length) * 100
  }
  
  def hammingDistance(that: Sequence) = {
    sequence.zip(that.sequence).count { case (a, b) => a != b}
  }
  
  def asDNA = new DNA(innerSequence)
  def asRNA = new RNA(innerSequence)
  
  private [sequences] def sequence = innerSequence
  
  override def toString = innerSequence
}

object Sequence {
  def newBuilder: Builder[Base, Sequence] = new ArrayBuffer mapResult indexedSeqToSequence
}