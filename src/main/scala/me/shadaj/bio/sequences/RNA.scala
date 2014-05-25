package me.shadaj.bio.sequences

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder
import scala.collection.generic.CanBuildFrom
import me.shadaj.bio.util.BitStorage
import me.shadaj.bio.codontable.CodonTable

class RNA private (storage: BitStorage, val length: Int) extends Sequence[RNABase, RNA] {
  import RNA._
  def seqBuilder: Builder[RNABase, RNA] = RNA.newBuilder
  
  def apply(index: Int): RNABase = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    storage(index, RNABase.fromInt)
  }
  
  def complement: RNA = {
    map {
      case A => U
      case U => A
      case G => C
      case C => G
    }
  }
  
  def reverseComplement: RNA = {
    reverse.complement
  }
  
  def toDNA: DNA = {
    DNA.fromSeq {
      map {
        case U => T
        case x: DNABase => x
      }
    }
  }
  
  def toProtein(implicit codonTable: CodonTable): Protein = {
    codonTable.proteinForRNA(this)
  }
}

object RNA {
  def apply(str: String): RNA = {
    fromSeq(str.map(RNABase.fromChar))
  }
  
  def fromSeq(seq: IndexedSeq[RNABase]): RNA = {
    new RNA(BitStorage(2, seq.toArray, RNABase.toInt), seq.length)
  }
  
  def newBuilder: Builder[RNABase, RNA] = (new ArrayBuffer).mapResult(fromSeq)
  
  implicit def canBuildFrom: CanBuildFrom[RNA, RNABase, RNA] = new CanBuildFrom[RNA, RNABase, RNA] {
    def apply() = newBuilder
    def apply(from: RNA) = newBuilder
  }
}