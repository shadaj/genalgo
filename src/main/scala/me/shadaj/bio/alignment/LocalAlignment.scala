package me.shadaj.bio.alignment

import scala.annotation.tailrec

import me.shadaj.bio.scoring.ScoringMatrix
import me.shadaj.bio.sequences.BaseLike
import me.shadaj.bio.sequences.Indel
import me.shadaj.bio.sequences.Sequence

object LocalAlignment extends AlignmentStrategy {
  def align[B <: BaseLike, C <: Sequence[B, _]](seq1: C, seq2: C, scorer: ScoringMatrix[B]): Alignment = {
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
    
    @tailrec
    def getAlignment(alignmentA: AlignmentSequence = new AlignmentSequence(IndexedSeq()), alignmentB: AlignmentSequence = new AlignmentSequence(IndexedSeq()), i: Int = seq1.length, j: Int = seq2.length): Alignment = {
      if (i == 0 && j == 0) {
        new Alignment(alignmentA, alignmentB, lengths(bestI)(bestJ))
      } else {
        if (i > 0 && j > 0 && lengths(i)(j) == (lengths(i - 1)(j - 1) + scorer.score(seq1(i - 1), seq2(j - 1)))) {
          getAlignment(seq1(i - 1) +: alignmentA, seq2(j - 1) +: alignmentB, i - 1, j - 1)
        } else if (i > 0 && lengths(i)(j) == (lengths(i - 1)(j) - scorer.indelPenalty)) {
          getAlignment(seq1(i - 1) +: alignmentA, Indel +: alignmentB, i - 1, j)
        } else if (j > 0 && lengths(i)(j) == (lengths(i)(j - 1) - scorer.indelPenalty)) {
          getAlignment(Indel +: alignmentA, seq2(j - 1) +: alignmentB, i, j - 1)
        } else {
          new Alignment(alignmentA, alignmentB, lengths(bestI)(bestJ))
        }
      }
    }
    

    getAlignment(i = bestI, j = bestJ)
  }
}