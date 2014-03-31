package me.shadaj.bio.sequences

trait AminoAcid extends BaseLike

object AminoAcid {
  val acids = List(Phe, Leu, Ser, Tyr, Cys, Trp, Pro, His, Gln, Arg, Ile, Met, Thr, Asn, Lys, Val, Ala, Asp, Glu, Gly)
  val fromChar = acids.map(a => a.short -> a).toMap
}

object Phe extends AminoAcid {
  val short = 'F'
  val long = "Phenylalanine"
}

object Leu extends AminoAcid {
  val short = 'L'
  val long = "Leucine"
}

object Ser extends AminoAcid {
  val short = 'S'
  val long = "Serine"
}

object Tyr extends AminoAcid {
  val short = 'Y'
  val long = "Tyrosine"
}

object Cys extends AminoAcid {
  val short = 'C'
  val long = "Cysteine"
}

object Trp extends AminoAcid {
  val short = 'W'
  val long = "Tryptophan"
}

object Pro extends AminoAcid {
  val short = 'P'
  val long = "Proline"
}

object His extends AminoAcid {
  val short = 'H'
  val long = "Histidine"
}

object Gln extends AminoAcid {
  val short = 'Q'
  val long = "Glutamine"
}

object Arg extends AminoAcid {
  val short = 'R'
  val long = "Arginine"
}

object Ile extends AminoAcid {
  val short = 'I'
  val long = "Isoleucine"
}

object Met extends AminoAcid {
  val short = 'M'
  val long = "Methionine"
}

object Thr extends AminoAcid {
  val short = 'T'
  val long = "Threonine"
}

object Asn extends AminoAcid {
  val short = 'N'
  val long = "Asparagine"
}

object Lys extends AminoAcid {
  val short = 'K'
  val long = "Lysine"
}

object Val extends AminoAcid {
  val short = 'V'
  val long = "Valine"
}

object Ala extends AminoAcid {
  val short = 'A'
  val long = "Alanine"
}

object Asp extends AminoAcid {
  val short = 'D'
  val long = "Aspartate"
}

object Glu extends AminoAcid {
  val short = 'E'
  val long = "Glutamate"
}

object Gly extends AminoAcid {
  val short = 'G'
  val long = "Glycine"
}