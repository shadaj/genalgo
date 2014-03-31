package me.shadaj.bio.sequences

import scala.collection.SeqLike
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder
import scala.collection.IndexedSeqLike

import Base._

class DNA private (innerStorage: Array[Int], val length: Int) extends Sequence[DNABase, DNA] {
  import DNA._

  override protected[this] def newBuilder: Builder[DNABase, DNA] = DNA.newBuilder

  def apply(index: Int): DNABase = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    DNABase.fromInt(innerStorage(index / LengthPerInt) >> (index % LengthPerInt * BitsPerGroup) & Bitmask)
  }

  def complement: DNA = {
    map {
      _ match {
        case A => T
        case T => A
        case G => C
        case C => G
      }
    }
  }

  def reverseComplement: DNA = {
    reverse complement
  }

  def toRNA: RNA = {
    RNA.fromSeq {
      map {
        _ match {
          case T => U
          case x: RNABase => x
        }
      }
    }
  }
}

object DNA {
  def apply(str: String): DNA = {
    fromSeq(str.map(DNABase.fromChar))
  }

  def fromSeq(seq: IndexedSeq[DNABase]): DNA = {
    val innerStorage = new Array[Int]((seq.length + Bitmask - 1) / Bitmask)
    for (i <- 0 until seq.length) {
      innerStorage(i / LengthPerInt) |= DNABase.toInt(seq(i)) << (i % Bitmask * BitsPerGroup)
    }

    new DNA(innerStorage, seq.length)
  }

  def newBuilder: Builder[DNABase, DNA] = new ArrayBuffer mapResult fromSeq

  implicit def canBuildFrom: CanBuildFrom[DNA, DNABase, DNA] = new CanBuildFrom[DNA, DNABase, DNA] {
    def apply() = newBuilder
    def apply(from: DNA) = newBuilder
  }
}