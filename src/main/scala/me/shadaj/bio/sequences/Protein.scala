package me.shadaj.bio.sequences

import scala.collection.IndexedSeqLike
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Builder

class Protein(innerSequence: String) extends Sequence[AminoAcid, Protein] {
  import Protein._
  
  def apply(index: Int) = AminoAcid.fromChar(innerSequence.apply(index))
  def length = innerSequence.length
  
  override protected[this] def newBuilder: Builder[AminoAcid, Protein] = Protein.newBuilder
}

object Protein {
  def apply(str: String): Protein = {
    fromSeq(str.map(AminoAcid.fromChar))
  }
  
  def fromSeq(seq: IndexedSeq[AminoAcid]) = new Protein(seq.mkString)
  
  def newBuilder: Builder[AminoAcid, Protein] = new ArrayBuffer mapResult fromSeq
  
  implicit def canBuildFrom = new CanBuildFrom[Protein, AminoAcid, Protein] {
    def apply() = newBuilder
    def apply(from: Protein) = newBuilder
  }
}