package me.shadaj.bio

package object sequences {
  implicit class SequenceString(val string: String) extends AnyVal {
    def asDNA: DNA = DNA.fromSeq(string.map(DNABase.fromChar))
    def asRNA: RNA = RNA.fromSeq(string.map(RNABase.fromChar))
    def asProtein: Protein = Protein.fromSeq(string.map(AminoAcid.fromChar))
  }
  
//  implicit class SequenceSeq(val seq: IndexedSeq[BaseLike]) extends AnyVal {
//    def asDNA: DNA = DNA.fromSeq(seq)
//    def asRNA: RNA = RNA.fromSeq(seq)
//  }
  
//  implicit def seqToSequence(seq: IndexedSeq[BaseLike]) = new Sequence(seq.map(_.toString).mkString)
}