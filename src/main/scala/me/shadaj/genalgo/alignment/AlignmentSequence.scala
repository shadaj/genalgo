package me.shadaj.genalgo.alignment

import scala.collection.TraversableView.NoBuilder
import scala.collection.generic.CanBuildFrom
import me.shadaj.genalgo.sequences._

import scala.collection.{IndexedSeqLike, mutable}

import AlignmentSequence._

class AlignmentSequence[B <: BaseLike, C <: BioSequence[B, _]](original: C, indelLocations: Array[Int])
  extends IndexedSeq[AlignmentBase[B]]
  with IndexedSeqLike[AlignmentBase[B], AlignmentSequence[B, C]] {
  val sortedIndels = indelLocations.sortBy(- _).zipWithIndex
  val indelsCount = indelLocations.length
  val lastIndelIndex = indelsCount - 1

  def originalBuilder = {
    original.newBuilder.asInstanceOf[mutable.Builder[B, C]]
  }

  final override def newBuilder: mutable.Builder[AlignmentBase[B], AlignmentSequence[B, C]] = {
    AlignmentSequence.builder[B, C](originalBuilder)
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

  def fromSeq[B <: BaseLike, C <: BioSequence[B, _]](seq: IndexedSeq[AlignmentBase[B]],
                                                     builder: mutable.Builder[B, C]) = {
    val (indelEithers, baseEithers) = seq.zipWithIndex.partition(_._1.isRight)
    val bases = baseEithers.map(_._1.left.get)
    val indels = indelEithers.map(_._2)

    builder ++= bases
    val newOriginal = builder.result()
    new AlignmentSequence[B, C](newOriginal, indels.toArray)
  }

  def builder[B <: BaseLike, C <: BioSequence[B, _]](builder: mutable.Builder[B, C]) = {
    IndexedSeq.newBuilder[AlignmentBase[B]].mapResult(fromSeq[B, C](_, builder))
  }

  implicit def canBuildFrom[B <: BaseLike, C <: BioSequence[B, _]] = {
    new CanBuildFrom[AlignmentSequence[B, C], AlignmentBase[B], AlignmentSequence[B, C]] {
      def apply() = new NoBuilder[AlignmentBase[B]]
      def apply(from: AlignmentSequence[B, C]): mutable.Builder[Either[B, Indel.type], AlignmentSequence[B, C]] = {
        builder[B, C](from.originalBuilder)
      }
    }
  }
}