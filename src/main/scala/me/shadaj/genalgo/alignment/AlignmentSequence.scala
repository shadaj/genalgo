package me.shadaj.genalgo.alignment

import scala.collection.TraversableView.NoBuilder
import scala.collection.generic.CanBuildFrom
import me.shadaj.genalgo.sequences._

import scala.collection.{IndexedSeqLike, mutable}

import AlignmentSequence._

final class AlignmentSequence[B <: BaseLike](private [AlignmentSequence] val original: BioSequence[B], indelLocations: Array[Int])
  extends IndexedSeq[AlignmentBase[B]]
  with IndexedSeqLike[AlignmentBase[B], AlignmentSequence[B]] {
  type C = this.type

  val sortedIndels = indelLocations.sortBy(- _).zipWithIndex
  val indelsCount = indelLocations.length
  val lastIndelIndex = indelsCount - 1

  def self = this
  override def newBuilder: mutable.Builder[AlignmentBase[B], AlignmentSequence[B]] = {
    AlignmentSequence.builder[B, original.C](original.newBuilder)
  }

  def apply(index: Int) = {
    sortedIndels.find(_._1 <= index).map { case (locationInSequence, locationInListFromEnd) =>
      if (locationInSequence == index) {
        Right(Indel)
      } else {
        val locationInList = lastIndelIndex - locationInListFromEnd
        Left(original(index - (locationInList + 1)))
      }
    }.getOrElse {
      Left(original(index))
    }
  }

  def length = original.length + indelsCount

  override def toString() = map(_.merge).mkString
}

object AlignmentSequence {
  type AlignmentBase[B] = Either[B, Indel.type]

  def fromSeq[B <: BaseLike, OC <: BioSequence[B]](seq: IndexedSeq[AlignmentBase[B]],
                                                     builder: mutable.Builder[B, OC]) = {
    val (indelEithers, baseEithers) = seq.zipWithIndex.partition(_._1.isRight)
    val bases = baseEithers.map(_._1.left.get)
    val indels = indelEithers.map(_._2)

    builder ++= bases
    val newOriginal = builder.result()
    new AlignmentSequence[B](newOriginal, indels.toArray)
  }

  def builder[B <: BaseLike, OC <: BioSequence[B]](builder: mutable.Builder[B, OC]) = {
    IndexedSeq.newBuilder[AlignmentBase[B]].mapResult(fromSeq[B, OC](_, builder))
  }

  implicit def canBuildFrom[B <: BaseLike, OC <: BioSequence[B]] = {
    new CanBuildFrom[AlignmentSequence[B], AlignmentBase[B], AlignmentSequence[B]] {
      def apply() = new NoBuilder[AlignmentBase[B]]
      def apply(from: AlignmentSequence[B]): mutable.Builder[Either[B, Indel.type], AlignmentSequence[B]] = {
        builder[B, from.original.C](from.original.newBuilder)
      }
    }
  }
}