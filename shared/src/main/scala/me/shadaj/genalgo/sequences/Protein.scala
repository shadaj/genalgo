package me.shadaj.genalgo.sequences

import scala.collection.{mutable, IndexedSeqLike}
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer

import me.shadaj.genalgo.util.BitStorage

final class Protein private(storage: BitStorage, val length: Int) extends BioSequence[AminoAcid] with IndexedSeqLike[AminoAcid, Protein] {
  type C = Protein

  def self = this
  override def seqBuilder: mutable.Builder[AminoAcid, Protein] = Protein.newBuilder

  def apply(index: Int): AminoAcid = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    storage(index, AminoAcid.fromInt)
  }
}

object Protein {
  def apply(str: String): Protein = {
    Protein(str.map(AminoAcid.fromChar))
  }

  def apply(bases: AminoAcid*): Protein = {
    Protein(bases.toIndexedSeq)
  }

  def apply(seq: IndexedSeq[AminoAcid]): Protein = {
    new Protein(BitStorage(5, seq.toArray, AminoAcid.toInt), seq.length)
  }
  
  def newBuilder: mutable.Builder[AminoAcid, Protein] = (new ArrayBuffer).mapResult(apply)
  
  implicit def canBuildFrom: CanBuildFrom[Protein, AminoAcid, Protein] = new CanBuildFrom[Protein, AminoAcid, Protein] {
    def apply() = newBuilder
    def apply(from: Protein) = newBuilder
  }
}