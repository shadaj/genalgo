package me.shadaj.bio

package object sequences {
  implicit class SequenceString(val string: String) extends AnyVal {
    def asDNA: DNA = new DNA(string)
    def asRNA: RNA = new RNA(string)
  }
  
  implicit def indexedSeqToSequence(seq: IndexedSeq[Base]) = new Sequence(seq.map(_.toString).mkString)
}