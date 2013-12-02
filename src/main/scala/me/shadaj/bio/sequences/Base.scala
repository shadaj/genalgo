package me.shadaj.bio.sequences

sealed abstract class Base(value: Char) {
  override def toString = value.toString
}
object A extends Base('A')
object T extends Base('T')
object G extends Base('G')
object C extends Base('C')
object U extends Base('U')

object Base {
  def fromChar(c: Char): Base = {
    c match {
      case 'A' => A
      case 'T' => T
      case 'G' => G
      case 'C' => C
      case 'U' => U
    }
  }
}