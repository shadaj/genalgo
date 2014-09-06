package me.shadaj.genalgo.alignment

import scala.annotation.tailrec

import me.shadaj.genalgo.scoring.ScoringMatrix
import me.shadaj.genalgo.sequences.BaseLike
import me.shadaj.genalgo.sequences.Indel
import me.shadaj.genalgo.sequences.BioSequence

object LocalAlignment extends AlignmentStrategy {
  def align[B <: BaseLike, C <: BioSequence[B, _]](seq1: C, seq2: C, scorer: ScoringMatrix[B]): Alignment[B, C] = {
    val lengths = Array.fill(seq1.length + 1, seq2.length + 1)(0)

    for (i <- 0 to seq1.length) {
      lengths(i)(0) = 0
    }

    for (j <- 0 to seq2.length) {
      lengths(0)(j) = 0
    }

    var bestI = 0
    var bestJ = 0
    
    for (i <- 1 to seq1.length; j <- 1 to seq2.length) {
      lengths(i)(j) = {
        val freeTaxi = 0
        val matchCase = lengths(i - 1)(j - 1) + scorer.score(seq1(i - 1), seq2(j - 1))
        val deleteCase = lengths(i - 1)(j) - scorer.indelPenalty
        val insertCase = lengths(i)(j - 1) - scorer.indelPenalty
        val value = freeTaxi max matchCase max deleteCase max insertCase
        if (value > lengths(bestI)(bestJ)) {
          bestI = i
          bestJ = j
        }
        value
      }
    }

    val indelsAtEndA = seq1.length - bestI
    val indelsAtEndB = seq2.length - bestJ

    @tailrec
    def getAlignment(indelsFromEndA: Array[Int] = Array(),
                     indelsFromEndB: Array[Int] = Array(),
                     fromEnd: Int = 0,
                     i: Int = bestI,
                     j: Int = bestJ): Alignment[B, C] = {
      if (i > 0 && j > 0 && lengths(i)(j) == (lengths(i - 1)(j - 1) + scorer.score(seq1(i - 1), seq2(j - 1)))) {
        getAlignment(indelsFromEndA, indelsFromEndB, fromEnd - 1, i - 1, j - 1)
      } else if (i > 0 && lengths(i)(j) == (lengths(i - 1)(j) - scorer.indelPenalty)) {
        getAlignment(indelsFromEndA, indelsFromEndB :+ fromEnd, fromEnd - 1, i - 1, j)
      } else if (j > 0 && lengths(i)(j) == (lengths(i)(j - 1) - scorer.indelPenalty)) {
        getAlignment(indelsFromEndA :+ fromEnd, indelsFromEndB, fromEnd - 1, i, j - 1)
      } else {
        val lastIndexA = indelsFromEndA.length + seq1.length - indelsAtEndA - 1
        val lastIndexB = indelsFromEndB.length + seq2.length - indelsAtEndB - 1
        val aIndels = indelsFromEndA.map(lastIndexA +)
        val bIndels = indelsFromEndB.map(lastIndexB +)
        new Alignment(
          new AlignmentSequence(
            seq1.drop(i).asInstanceOf[C].dropRight(indelsAtEndA).asInstanceOf[C],
            aIndels.map(_ - i)),
          new AlignmentSequence(
            seq2.drop(j).asInstanceOf[C].dropRight(indelsAtEndB).asInstanceOf[C],
            bIndels.map(_ - j)),
          lengths(bestI)(bestJ))
      }
    }

    getAlignment()
  }
}