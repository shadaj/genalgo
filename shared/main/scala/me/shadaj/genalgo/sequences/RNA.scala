package me.shadaj.genalgo.sequences

import scala.collection.{mutable, IndexedSeqLike}
import scala.collection.mutable.ArrayBuffer
import scala.collection.generic.CanBuildFrom
import me.shadaj.genalgo.util.BitStorage
import me.shadaj.genalgo.proteins.codontable.CodonTable

final class RNA private[sequences] (storage: BitStorage, val length: Int) extends BioSequence[RNABase] with IndexedSeqLike[RNABase, RNA] {
  type C = RNA

  def self = this
  override def seqBuilder: mutable.Builder[RNABase, RNA] = RNA.newBuilder
  
  def apply(index: Int): RNABase = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    storage(index, RNABase.fromInt)
  }
  
  def complement: RNA = {
    new RNA(storage.complement, length)
  }
  
  def reverseComplement: RNA = {
    reverse.complement
  }
  
  def toDNA: DNA = {
    new DNA(storage, length)
  }
  
  def toProtein(implicit codonTable: CodonTable): Protein = {
    codonTable.proteinForRNA(this)
  }
}

object RNA {
  def apply(str: String): RNA = {
    RNA(str.map(RNABase.fromChar))
  }

  def apply(bases: RNABase*): RNA = {
    RNA(bases.toIndexedSeq)
  }

  def apply(seq: IndexedSeq[RNABase]): RNA = {
    new RNA(BitStorage(2, seq.toArray, RNABase.toInt), seq.length)
  }
  
  def newBuilder: mutable.Builder[RNABase, RNA] = (new ArrayBuffer).mapResult(apply)
  
  implicit def canBuildFrom: CanBuildFrom[RNA, RNABase, RNA] = new CanBuildFrom[RNA, RNABase, RNA] {
    def apply() = newBuilder
    def apply(from: RNA) = newBuilder
  }
}