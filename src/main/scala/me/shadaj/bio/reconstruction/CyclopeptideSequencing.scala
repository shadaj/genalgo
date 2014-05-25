package me.shadaj.bio.reconstruction

trait CyclopeptideSequencer {
  def sequence(spectrum: Seq[Int]): Seq[Int]
}

class ConvolutionLeaderboardSequencing(convolutionSize: Int, leaderboardSize: Int) extends CyclopeptideSequencer {
  def sequence(originalSpectrum: Seq[Int]) = {
    val spectrum = (0 +: originalSpectrum).sorted

    def createConvolution(spectrum: Seq[Int]) = {
      for (startIndex <- 0 until spectrum.length;
           endIndex <- startIndex + 1 until spectrum.length) yield spectrum(endIndex) - spectrum(startIndex)
    }

    val convolution = createConvolution(spectrum).filter(n => n >= 57 && n <= 200)

    def takeTopWithTies[T, C](seq: Seq[T], toCompare: T => C, toTake: Int) = {
      val (normal, remaining) = seq.splitAt(toTake)
      val last = toCompare(normal.last)
      val ties = remaining.takeWhile(e => toCompare(e) == last)
      normal ++ ties
    }

    val alphabet = takeTopWithTies[Int, Int](convolution.distinct.sortBy(n => -convolution.count(_ == n)), n => convolution.count(_ == n), convolutionSize).sorted

    val parentMass = spectrum.last

    def mass(peptide: Seq[Int]) = {
      peptide.sum
    }

    def cycloSpectrum(peptide: Seq[Int]) = {
      def peptideAt(index: Int) = {
        val normalizedIndex = (index + peptide.length) % peptide.length
        peptide(normalizedIndex)
      }

      def subpeptide(start: Int, length: Int) = {
        (start until start + length).map(peptideAt)
      }

      for (index <- 0 until peptide.length; length <- 0 to peptide.length) yield {
        subpeptide(index, length).sum
      }
    }

    def score(peptide: Seq[Int]): Int = {
      val peptideSpectrum = cycloSpectrum(peptide)
      spectrum.distinct.count(peptideSpectrum.contains)
    }

    var leaderboard = Seq((Seq[Int](), score(Seq[Int]())))
    var leaderPeptide = leaderboard.head

    def cut(length: Int) {
      leaderboard = takeTopWithTies[(Seq[Int], Int), Int](leaderboard.sortBy(t => -t._2), _._2, length)
    }

    def expandAndFilter(seq: Seq[(Seq[Int], Int)]) = {
      for (p <- seq; add <- alphabet; peptide = p._1 :+ add; peptideScore = score(peptide); peptideMass = mass(peptide) if peptideMass <= parentMass) yield {
        val inLeaderboard = (peptide, peptideScore)
        inLeaderboard
      }
    }

    while (leaderboard.nonEmpty) {
      leaderboard = expandAndFilter(leaderboard)
      if (leaderboard.nonEmpty) {
        val max = leaderboard.maxBy(_._2)
        if (max._2 >= leaderPeptide._2) {
          leaderPeptide = max
        }
        cut(leaderboardSize)
      }
    }

    leaderPeptide._1
  }
}
