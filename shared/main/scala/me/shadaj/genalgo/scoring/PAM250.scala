package me.shadaj.genalgo.scoring

import me.shadaj.genalgo.Resources
import me.shadaj.genalgo.sequences.AminoAcid

class PAM250(val indelPenalty: Int) extends ScoringMatrix[AminoAcid] {
  import PAM250._

  def score(b1: AminoAcid, b2: AminoAcid) = {
    scorer((b1, b2))
  }
}

object PAM250 {
  private val lines = Resources.pam250
  private val xAxis = lines.head.trim.split(' ').filter(_ != "").map(_.head)
  private val scorer = lines.tail.flatMap { s =>
    val y = AminoAcid.fromChar(s.head)
    s.tail.trim.split(' ').filter(s => s != " " && s != "").map(_.toInt).zipWithIndex.map { case (score, index) =>
      (AminoAcid.fromChar(xAxis(index)), y) -> score
    }
  }.toMap
}
