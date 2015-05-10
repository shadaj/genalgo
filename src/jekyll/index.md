---
layout: default
---

<img class="center-block" style="max-height: 75vh; max-width: 50vw" src="/assets/genalgoLogo.svg">
<p style="font-size: 300%" class="lead text-center">A bioinformatics library for Scala (and Scala.js)</p>

## Features
<hr/>
### Creating Sequences
genalgo provides implementations for DNA, RNA, and Protein

Any genalgo sequence is an IndexedSeq thus you can use all standard collections methods such as map, filter, etc.

##### From a string
```scala
val myDNA = DNA("AT")
val myRNA = RNA("AU")
val myProtein = Protein("ME")
```

##### From bases
```scala
val myDNA = DNA(A, T)
val myRNA = RNA(A, U)
val myProtein = Protein(Met, Glu)
```

##### From bases (in an IndexedSeq)
```scala
val myDNA = DNA(IndexedSeq(A, T))
val myRNA = RNA(IndexedSeq(A, U))
val myProtein = Protein(IndexedSeq(Met, Glu))
```

### Transformations

##### Complement
```scala
myDNA.complement
myRNA.complement
```

### Conversions

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
Retrieving data from UniProt returns a Future[FASTA] on which you can set callbacks and apply transformations

```scala
Uniprot.getFasta("Q9D103")
```

### Example: Aligning the IFM1 protein from mice and humans and generating an image from the alignment
```scala
Uniprot.getFasta("Q9D103").zip(Uniprot.getFasta("P13164")).map { case (mouse, human) =>
  val alignment = mouse.sequence.align(human.sequence, new BLOSUM62(5))
  println(alignment)
  alignment.toImageFile(new File("obscurin.png"))
}
```


## Demos
<hr/>
### Align sequences from [UniProt]("http://uniprot.org") (<a id="prefillButton">prefill values</a>)
<div class="well col-lg-4">
  <fieldset>
    <input id="uniprot1" type="text" class="form-control" placeholder="Uniprot ID 1"></input>
    <input id="uniprot2" type="text" class="form-control" placeholder="Uniprot ID 2"></input>
    <button id="align-btn" style="margin-top: 20px" class="btn btn-lg btn-primary">Align!</button>
  </fieldset>
</div>
<div class="col-lg-8">
  <textarea readonly wrap="off" rows="5" style="font-family: monospace; width: 100%" id="output">Results will show up here</textarea>
</div>
