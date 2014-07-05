package me.shadaj.genalgo.sequences

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
  // A and T (and G and C) bit representations are complementary with this ordering
  // DNA bits can be reused for RNA because T and U share the same index
  // See RNABase
  val fromInt = Array(A, C, G, T)
  val charMap = fromInt.map(b => b.short -> b).toMap
  def fromChar(c: Char) = {
    charMap.getOrElse(c, throw new IllegalArgumentException("Non-DNA Base found: " + c))
  }
  val toInt: DNABase => Int = fromInt.zipWithIndex.toMap
}

trait RNABase extends Base
object RNABase {
  val fromInt = Array(A, C, G, U)
  val charMap = fromInt.map(b => b.short -> b).toMap
  def fromChar(c: Char) = {
    charMap.getOrElse(c, throw new IllegalArgumentException("Non-RNA Base found: " + c))
  }
  val toInt: RNABase => Int = fromInt.zipWithIndex.toMap
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