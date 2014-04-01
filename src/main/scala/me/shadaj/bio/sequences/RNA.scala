package me.shadaj.bio.sequences

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder
import scala.collection.IndexedSeqLike
import scala.collection.generic.CanBuildFrom

import Base._

class RNA private (innerStorage: Array[Int], val length: Int) extends Sequence[RNABase, RNA] {
  import RNA._
  override protected[this] def newBuilder: Builder[RNABase, RNA] = RNA.newBuilder
  
  def apply(index: Int): RNABase = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    RNABase.fromInt(innerStorage(index / LengthPerInt) >> (index % LengthPerInt * BitsPerGroup) & Bitmask)
  }
  
  def complement = {
    map { base =>
      base match {
        case A => U
        case U => A
        case G => C
        case C => G
      }
    }
  }
  
  def reverseComplement = {
    reverse complement
  }
  
  def toDNA: DNA = {
    DNA.fromSeq {
      map {
        _ match {
          case U => T
          case x: DNABase => x
        }
      }
    }
  }
}

object RNA {
  def apply(str: String): RNA = {
    fromSeq(str.map(RNABase.fromChar))
  }
  
  def fromSeq(seq: IndexedSeq[RNABase]): RNA = {
    val innerStorage = new Array[Int]((seq.length + Bitmask - 1) / Bitmask)
    for (i <- 0 until seq.length) {
      seq(i) match {
        case b: RNABase => {
          innerStorage(i / LengthPerInt) |= RNABase.toInt(b) << (i % Bitmask * BitsPerGroup)
        }
        case other => throw new IllegalArgumentException("Non-RNA BaseLike encountered: " + other)
      }
      
    }
    
    new RNA(innerStorage, seq.length)
  }
  
  def newBuilder: Builder[RNABase, RNA] = new ArrayBuffer mapResult fromSeq
  
  implicit def canBuildFrom = new CanBuildFrom[RNA, RNABase, RNA] {
    def apply() = newBuilder
    def apply(from: RNA) = newBuilder
  }
}