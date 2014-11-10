package me.shadaj.genalgo.alignment

import scala.annotation.tailrec

import me.shadaj.genalgo.scoring.ScoringMatrix
import me.shadaj.genalgo.sequences.BaseLike
import me.shadaj.genalgo.sequences.BioSequence

import scala.collection.IndexedSeqLike

object GlobalAlignment extends AlignmentStrategy {
  def align[B <: BaseLike, C <: BioSequence[B] with IndexedSeqLike[B, C]]
    (seq1: C, seq2: C, scorer: ScoringMatrix[B]): Alignment[B, C] = {
    val lengths = Array.fill(seq1.length + 1, seq2.length + 1)(0)

    for (i <- 1 to seq1.length) {
      lengths(i)(0) = -(i * scorer.indelPenalty)
    }

    for (j <- 1 to seq2.length) {
      lengths(0)(j) = -(j * scorer.indelPenalty)
    }
    
    for (i <- 1 to seq1.length; j <- 1 to seq2.length) {
      lengths(i)(j) = {
        val matchCase = lengths(i - 1)(j - 1) + scorer.score(seq1(i - 1), seq2(j - 1))
        val deleteCase = lengths(i - 1)(j) - scorer.indelPenalty
        val insertCase = lengths(i)(j - 1) - scorer.indelPenalty
        matchCase max deleteCase max insertCase
      }
    }

    @tailrec
    def getAlignment(indelsFromEndA: Array[Int] = Array(),
                     indelsFromEndB: Array[Int] = Array(),
                     fromEnd: Int = 0,
                     i: Int = seq1.length,
                     j: Int = seq2.length): Alignment[B, C] = {
      if (i == 0 && j == 0) {
        val lastIndex = indelsFromEndA.length + seq1.length - 1
        val aIndels = indelsFromEndA.map(lastIndex +)
        val bIndels = indelsFromEndB.map(lastIndex +)
        new Alignment(
          new AlignmentSequence(seq1, aIndels),
          new AlignmentSequence(seq2, bIndels),
          lengths(seq1.length)(seq2.length))
      } else {
        if (i > 0 && j > 0 && lengths(i)(j) == (lengths(i - 1)(j - 1) + scorer.score(seq1(i - 1), seq2(j - 1)))) {
          getAlignment(indelsFromEndA, indelsFromEndB, fromEnd - 1, i - 1, j - 1)
        } else if (i > 0 && lengths(i)(j) == (lengths(i - 1)(j) - scorer.indelPenalty)) {
          getAlignment(indelsFromEndA, indelsFromEndB :+ fromEnd, fromEnd - 1, i - 1, j)
        } else {
          getAlignment(indelsFromEndA :+ fromEnd, indelsFromEndB, fromEnd - 1, i, j - 1)
        }
      }
    }
    

    getAlignment()
  }
}