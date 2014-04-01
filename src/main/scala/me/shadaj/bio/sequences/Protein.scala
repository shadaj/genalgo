package me.shadaj.bio.sequences

import scala.collection.IndexedSeqLike
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder

import me.shadaj.bio.util.BitStorage

class Protein(storage: BitStorage, val length: Int) extends Sequence[AminoAcid, Protein] {
  import Protein._
  
  def apply(index: Int): AminoAcid = {
    if (index < 0 || index >= length) throw new IndexOutOfBoundsException
    storage(index, AminoAcid.fromInt)
  }
  
  override protected[this] def newBuilder: Builder[AminoAcid, Protein] = Protein.newBuilder
}

object Protein {
  def apply(str: String): Protein = {
    fromSeq(str.map(AminoAcid.fromChar))
  }
  
  def fromSeq(seq: IndexedSeq[AminoAcid]): Protein = {
    new Protein(BitStorage(5, seq.toArray, AminoAcid.toInt), seq.length)
  }
  
  def newBuilder: Builder[AminoAcid, Protein] = new ArrayBuffer mapResult fromSeq
  
  implicit def canBuildFrom: CanBuildFrom[Protein, AminoAcid, Protein] = new CanBuildFrom[Protein, AminoAcid, Protein] {
    def apply() = newBuilder
    def apply(from: Protein) = newBuilder
  }
}