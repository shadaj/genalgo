package me.shadaj.bio.sequences

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder
import scala.collection.IndexedSeqLike

class RNA(innerSequence: String) extends Sequence(innerSequence) with IndexedSeqLike[Base, RNA] {
  override protected[this] def newBuilder: Builder[Base, RNA] = RNA.newBuilder
  
  def complement = {
    map { base =>
      base match {
        case A => U
        case U => A
        case G => C
        case C => G
      }
    }.asRNA
  }
  
  def reverseComplement = {
    reverse complement
  }
}

object RNA {
  def fromSeq(seq: IndexedSeq[Base]) = {
    val sequence: Sequence = seq
    sequence.asRNA
  }
  
  def newBuilder: Builder[Base, RNA] = new ArrayBuffer mapResult fromSeq
}