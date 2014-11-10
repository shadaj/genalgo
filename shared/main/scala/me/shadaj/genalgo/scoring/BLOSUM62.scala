package me.shadaj.genalgo.scoring

import me.shadaj.genalgo.Resources
import me.shadaj.genalgo.sequences.AminoAcid

class BLOSUM62(val indelPenalty: Int) extends ScoringMatrix[AminoAcid] {
  import BLOSUM62._

  def score(b1: AminoAcid, b2: AminoAcid) = {
    scorer((b1, b2))
  }
}

object BLOSUM62 {
  private val lines = Resources.blosum62
  private val xAxis = lines.head.trim.split("  ").map(s => AminoAcid.fromChar(s.head))
  private val scorer = lines.tail.flatMap { s =>
    val y = AminoAcid.fromChar(s.head)
    s.drop(2).split(' ').filter(_ != "").map(_.toInt).zipWithIndex.map { case (score, index) =>
      (xAxis(index), y) -> score
    }
  }.toMap
}
