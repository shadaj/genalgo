package me.shadaj.genalgo.sequences

import scala.collection.{mutable, IndexedSeqLike}
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer

import me.shadaj.genalgo.util.BitStorage


final class DNA private[sequences] (storage: BitStorage, val length: Int) extends BioSequence[DNABase] with IndexedSeqLike[DNABase, DNA] {
  override type C = DNA

  def self = this
  override def seqBuilder: mutable.Builder[DNABase, DNA] = DNA.newBuilder

  def apply(index: Int): DNABase = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    storage(index, DNABase.fromInt)
  }

  def complement: DNA = {
    new DNA(storage.complement, length)
  }

  def reverseComplement: DNA = {
    reverse.complement
  }

  def toRNA: RNA = {
    new RNA(storage, length)
  }
}

object DNA {
  def apply(str: String): DNA = {
    DNA(str.map(DNABase.fromChar))
  }

  def apply(bases: DNABase*): DNA = {
    DNA(bases.toIndexedSeq)
  }

  def apply(seq: IndexedSeq[DNABase]): DNA = {
    new DNA(BitStorage(2, seq.toArray, DNABase.toInt), seq.length)
  }

  def newBuilder: mutable.Builder[DNABase, DNA] = (new ArrayBuffer).mapResult(apply)

  implicit def canBuildFrom: CanBuildFrom[DNA, DNABase, DNA] = new CanBuildFrom[DNA, DNABase, DNA] {
    def apply() = newBuilder
    def apply(from: DNA) = newBuilder
  }
}