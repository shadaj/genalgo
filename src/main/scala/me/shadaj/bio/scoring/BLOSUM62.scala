package me.shadaj.bio.scoring

import scala.io.Source
import me.shadaj.bio.sequences.AminoAcid

class BLOSUM62(val indelPenalty: Int) extends ScoringMatrix[AminoAcid] {
  import BLOSUM62._
  
  def score(b1: AminoAcid, b2: AminoAcid) = {
    scorer((b1, b2))
  }
}

object BLOSUM62 {
  private val source = Source.fromInputStream(getClass().getResourceAsStream("/blosum62.txt")).getLines
  private val xAxis = source.next.trim.split("  ").map(s => AminoAcid.fromChar(s.head))
  private val scorer = source.flatMap { s =>
    val y = AminoAcid.fromChar(s.head)
    s.drop(2).split(' ').filter(_ != "").map(_.toInt).zipWithIndex.map { case (score, index) =>
      (xAxis(index), y) -> score
    }
  }.toMap
}