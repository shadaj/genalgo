# genalgo

A bioinformatics library for Scala

[![Build Status](https://travis-ci.org/shadaj/genalgo.svg?branch=master)](https://travis-ci.org/shadaj/genalgo)

## Usage

### Creating Sequences
genalgo provides implementations for `DNA`, `RNA`, and `Protein`

Any genalgo sequence is an `IndexedSeq` thus you can use all standard collections methods such as `map`, `filter`, etc.

#### From a string
```scala
val myDNA = DNA("AT")
val myRNA = RNA("AU")
val myProtein = Protein("ME")
```

#### From bases
```scala
val myDNA = DNA(A, T)
val myRNA = RNA(A, U)
val myProtein = Protein(Met, Glu)
```

#### From bases (in an IndexedSeq)
```scala
val myDNA = DNA(IndexedSeq(A, T))
val myRNA = RNA(IndexedSeq(A, U))
val myProtein = Protein(IndexedSeq(Met, Glu))
```

#### Transformations

##### Complement
```scala
myDNA.complement
myRNA.complement
```

#### Conversions

##### DNA to RNA (and vice-versa)
```scala
myDNA.toRNA
myRNA.toDNA
```

##### RNA to Protein
```scala
myRNA.toProtein
```

### Alignment
```scala
Protein("PLEASANTLY").align(Protein("MEANLY"), new BLOSUM62(5))
```

### UniProt
Retrieving data from UniProt returns a `Future[FASTA]` on which you can set callbacks and apply transformations
```scala
Uniprot.getFasta("A2AAJ9")
```

## Example: Aligning the obscurin protein from [mice](http://beta.uniprot.org/uniprot/A2AAJ9) and [humans](http://beta.uniprot.org/uniprot/Q5VST9) and generating an image from the alignment
```scala
Uniprot.getFasta("A2AAJ9").zip(Uniprot.getFasta("Q5VST9")).map { case (mouse, human) =>
  val alignment = mouse.sequence.align(human.sequence, new BLOSUM62(5))
  println(alignment)
  alignment.toImageFile(new File("obscurin.png"))
}
```
