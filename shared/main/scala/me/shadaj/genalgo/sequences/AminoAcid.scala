package me.shadaj.genalgo.sequences

trait AminoAcid extends BaseLike {
  lazy val name = getClass.getSimpleName
}

trait Hydrophobic
trait Hydrophilic
trait Charged
trait NegativelyCharged extends Charged
trait PositivelyCharged extends Charged

object AminoAcid {
  val fromInt = Array(Phe, Leu, Ser, Tyr, Cys, Trp, Pro, His, Gln, Arg, Ile, Met, Thr, Asn, Lys, Val, Ala, Asp, Glu, Gly, Stop)
  val charMap = fromInt.map(b => b.short -> b).toMap
  def fromChar(c: Char) = {
    charMap.getOrElse(c, throw new IllegalArgumentException("Non-Amino Acid Base found: " + c))
  }
  val toInt: AminoAcid => Int = fromInt.zipWithIndex.toMap
}

object Phe extends AminoAcid with Hydrophobic {
  val short = 'F'
  val long = "Phenylalanine"
}

object Leu extends AminoAcid with Hydrophobic {
  val short = 'L'
  val long = "Leucine"
}

object Ser extends AminoAcid with Hydrophilic {
  val short = 'S'
  val long = "Serine"
}

object Tyr extends AminoAcid with Hydrophilic {
  val short = 'Y'
  val long = "Tyrosine"
}

object Cys extends AminoAcid with Hydrophilic {
  val short = 'C'
  val long = "Cysteine"
}

object Trp extends AminoAcid with Hydrophobic {
  val short = 'W'
  val long = "Tryptophan"
}

object Pro extends AminoAcid with Hydrophobic {
  val short = 'P'
  val long = "Proline"
}

object His extends AminoAcid with PositivelyCharged {
  val short = 'H'
  val long = "Histidine"
}

object Gln extends AminoAcid with Hydrophilic {
  val short = 'Q'
  val long = "Glutamine"
}

object Arg extends AminoAcid with PositivelyCharged {
  val short = 'R'
  val long = "Arginine"
}

object Ile extends AminoAcid with Hydrophobic {
  val short = 'I'
  val long = "Isoleucine"
}

object Met extends AminoAcid with Hydrophobic {
  val short = 'M'
  val long = "Methionine"
}

object Thr extends AminoAcid with Hydrophilic {
  val short = 'T'
  val long = "Threonine"
}

object Asn extends AminoAcid with Hydrophilic {
  val short = 'N'
  val long = "Asparagine"
}

object Lys extends AminoAcid with PositivelyCharged {
  val short = 'K'
  val long = "Lysine"
}

object Val extends AminoAcid with Hydrophobic {
  val short = 'V'
  val long = "Valine"
}

object Ala extends AminoAcid with Hydrophobic {
  val short = 'A'
  val long = "Alanine"
}

object Asp extends AminoAcid with NegativelyCharged {
  val short = 'D'
  val long = "Aspartate"
}

object Glu extends AminoAcid with NegativelyCharged {
  val short = 'E'
  val long = "Glutamate"
}

object Gly extends AminoAcid with Hydrophilic {
  val short = 'G'
  val long = "Glycine"
}

object Stop extends AminoAcid {
  val short = '*'
  val long = "STOP"
}