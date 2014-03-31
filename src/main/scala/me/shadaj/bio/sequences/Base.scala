package me.shadaj.bio.sequences

trait BaseLike {
  def short: Char
  def long: String
  override def toString = short.toString
}

trait Base extends BaseLike
object Base {
  val BitsPerGroup = 2
  val LengthPerInt = 32 / BitsPerGroup
  val Bitmask = (1 << BitsPerGroup) - 1
}

trait DNABase extends Base
object DNABase {
  val charMap = Map('A' -> A, 'C' -> C, 'G' -> G, 'T' -> T)
  def fromChar(c: Char) = {
    charMap.getOrElse(c, throw new IllegalArgumentException("Non-DNA Base found: " + c))
  }
  val fromInt: Int => DNABase = Array(A, C, G, T)
  val toInt: DNABase => Int = Map(A -> 0, C -> 1, G -> 2, T -> 3)
}

trait RNABase extends Base
object RNABase {
  val charMap = Map('A' -> A, 'C' -> C, 'G' -> G, 'U' -> U)
  def fromChar(c: Char) = {
    charMap.getOrElse(c, throw new IllegalArgumentException("Non-RNA Base found: " + c))
  }
  val fromInt: Int => RNABase = Array(A, C, G, U)
  val toInt: RNABase => Int = Map(A -> 0, C -> 1, G -> 2, U -> 3)
}

case object A extends DNABase with RNABase {
  val short = 'A'
  val long = "adenine"
}

case object G extends DNABase with RNABase {
  val short = 'G'
  val long = "guanine"
}

case object C extends DNABase with RNABase {
  val short = 'C'
  val long = "cytosine"
}

case object T extends DNABase {
  val short = 'T'
  val long = "thymine"
}

case object U extends RNABase {
  val short = 'U'
  val long = "uracil"
}

case object Indel extends BaseLike {
  val short = '-'
  val long = "-"
}