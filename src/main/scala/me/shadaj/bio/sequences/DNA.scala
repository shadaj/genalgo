package me.shadaj.bio.sequences

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder
import scala.collection.IndexedSeqLike

class DNA(innerSequence: String) extends Sequence(innerSequence)  with IndexedSeqLike[Base, DNA] {
  override protected[this] def newBuilder: Builder[Base, DNA] = DNA.newBuilder
  
  def complement: DNA = {
    map { _ match {
        case A => T
        case T => A
        case G => C
        case C => G
      }
    }.asDNA
  }
  
  def reverseComplement: DNA = {
    reverse complement
  }
  
  def toRNA: RNA = {
    map { _ match {
        case T => U
        case x => x
      }
    }.asRNA
  }
}

object DNA {
  def fromSeq(seq: IndexedSeq[Base]) = {
    val sequence: Sequence = seq
    sequence.asDNA
  }
  
  def newBuilder: Builder[Base, DNA] = new ArrayBuffer mapResult fromSeq
}