package me.shadaj.bio.genotypes

case class Gene(allele1: Allele, allele2: Allele) {
  val allelesAsList = List(allele1, allele2)
}

sealed class Allele

object Dominant extends Allele {
  override def toString = "A"
}
object Recessive extends Allele {
  override def toString = "a"
}