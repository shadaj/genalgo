package me.shadaj.genalgo
object Resources {
val pam250 = List("   A  C  D  E  F  G  H  I  K  L  M  N  P  Q  R  S  T  V  W  Y", "A  2 -2  0  0 -3  1 -1 -1 -1 -2 -1  0  1  0 -2  1  1  0 -6 -3", "C -2 12 -5 -5 -4 -3 -3 -2 -5 -6 -5 -4 -3 -5 -4  0 -2 -2 -8  0", "D  0 -5  4  3 -6  1  1 -2  0 -4 -3  2 -1  2 -1  0  0 -2 -7 -4", "E  0 -5  3  4 -5  0  1 -2  0 -3 -2  1 -1  2 -1  0  0 -2 -7 -4", "F -3 -4 -6 -5  9 -5 -2  1 -5  2  0 -3 -5 -5 -4 -3 -3 -1  0  7", "G  1 -3  1  0 -5  5 -2 -3 -2 -4 -3  0  0 -1 -3  1  0 -1 -7 -5", "H -1 -3  1  1 -2 -2  6 -2  0 -2 -2  2  0  3  2 -1 -1 -2 -3  0", "I -1 -2 -2 -2  1 -3 -2  5 -2  2  2 -2 -2 -2 -2 -1  0  4 -5 -1", "K -1 -5  0  0 -5 -2  0 -2  5 -3  0  1 -1  1  3  0  0 -2 -3 -4", "L -2 -6 -4 -3  2 -4 -2  2 -3  6  4 -3 -3 -2 -3 -3 -2  2 -2 -1", "M -1 -5 -3 -2  0 -3 -2  2  0  4  6 -2 -2 -1  0 -2 -1  2 -4 -2", "N  0 -4  2  1 -3  0  2 -2  1 -3 -2  2  0  1  0  1  0 -2 -4 -2", "P  1 -3 -1 -1 -5  0  0 -2 -1 -3 -2  0  6  0  0  1  0 -1 -6 -5", "Q  0 -5  2  2 -5 -1  3 -2  1 -2 -1  1  0  4  1 -1 -1 -2 -5 -4", "R -2 -4 -1 -1 -4 -3  2 -2  3 -3  0  0  0  1  6  0 -1 -2  2 -4", "S  1  0  0  0 -3  1 -1 -1  0 -3 -2  1  1 -1  0  2  1 -1 -2 -3", "T  1 -2  0  0 -3  0 -1  0  0 -2 -1  0  0 -1 -1  1  3  0 -5 -3", "V  0 -2 -2 -2 -1 -1 -2  4 -2  2  2 -2 -1 -2 -2 -1  0  4 -6 -2", "W -6 -8 -7 -7  0 -7 -3 -5 -3 -2 -4 -4 -6 -5  2 -2 -5 -6 17  0", "Y -3  0 -4 -4  7 -5  0 -1 -4 -1 -2 -2 -5 -4 -4 -3 -3 -2  0 10")
object codonTables {
val standard = List("TTT F Phe	TTC F Phe	TTA L Leu	TTG L Leu", "TCT S Ser	TCC S Ser	TCA S Ser	TCG S Ser", "TAT Y Tyr	TAC Y Tyr	TAA * Ter	TAG * Ter", "TGT C Cys	TGC C Cys	TGA * Ter	TGG W Trp", "CTT L Leu	CTC L Leu	CTA L Leu	CTG L Leu", "CCT P Pro	CCC P Pro	CCA P Pro	CCG P Pro", "CAT H His	CAC H His	CAA Q Gln	CAG Q Gln", "CGT R Arg	CGC R Arg	CGA R Arg	CGG R Arg", "ATT I Ile	ATC I Ile	ATA I Ile	ATG M Met", "ACT T Thr	ACC T Thr	ACA T Thr	ACG T Thr", "AAT N Asn	AAC N Asn	AAA K Lys	AAG K Lys", "AGT S Ser	AGC S Ser	AGA R Arg	AGG R Arg", "GTT V Val	GTC V Val	GTA V Val	GTG V Val", "GCT A Ala	GCC A Ala	GCA A Ala	GCG A Ala", "GAT D Asp	GAC D Asp	GAA E Glu	GAG E Glu", "GGT G Gly	GGC G Gly	GGA G Gly	GGG G Gly")
val vertebrate_mitochondrial = List("TTT F Phe	TTC F Phe	TTA L Leu	TTG L Leu", "TCT S Ser	TCC S Ser	TCA S Ser	TCG S Ser", "TAT Y Tyr	TAC Y Tyr	TAA * Ter	TAG * Ter", "TGT C Cys	TGC C Cys	TGA W Trp	TGG W Trp", "CTT L Leu	CTC L Leu	CTA L Leu	CTG L Leu", "CCT P Pro	CCC P Pro	CCA P Pro	CCG P Pro", "CAT H His	CAC H His	CAA Q Gln	CAG Q Gln", "CGT R Arg	CGC R Arg	CGA R Arg	CGG R Arg", "ATT I Ile	ATC I Ile	ATA M Met	ATG M Met", "ACT T Thr	ACC T Thr	ACA T Thr	ACG T Thr", "AAT N Asn	AAC N Asn	AAA K Lys	AAG K Lys", "AGT S Ser	AGC S Ser	AGA * Ter	AGG * Ter", "GTT V Val	GTC V Val	GTA V Val	GTG V Val", "GCT A Ala	GCC A Ala	GCA A Ala	GCG A Ala", "GAT D Asp	GAC D Asp	GAA E Glu	GAG E Glu", "GGT G Gly	GGC G Gly	GGA G Gly	GGG G Gly")
}
val protein_mass_table = List("G 57", "A 71", "S 87", "P 97", "V 99", "T 101", "C 103", "I 113", "L 113", "N 114", "D 115", "K 128", "Q 128", "E 129", "M 131", "H 137", "F 147", "R 156", "Y 163", "W 186")
val blosum62 = List("   A  C  D  E  F  G  H  I  K  L  M  N  P  Q  R  S  T  V  W  Y", "A  4  0 -2 -1 -2  0 -2 -1 -1 -1 -1 -2 -1 -1 -1  1  0  0 -3 -2", "C  0  9 -3 -4 -2 -3 -3 -1 -3 -1 -1 -3 -3 -3 -3 -1 -1 -1 -2 -2", "D -2 -3  6  2 -3 -1 -1 -3 -1 -4 -3  1 -1  0 -2  0 -1 -3 -4 -3", "E -1 -4  2  5 -3 -2  0 -3  1 -3 -2  0 -1  2  0  0 -1 -2 -3 -2", "F -2 -2 -3 -3  6 -3 -1  0 -3  0  0 -3 -4 -3 -3 -2 -2 -1  1  3", "G  0 -3 -1 -2 -3  6 -2 -4 -2 -4 -3  0 -2 -2 -2  0 -2 -3 -2 -3", "H -2 -3 -1  0 -1 -2  8 -3 -1 -3 -2  1 -2  0  0 -1 -2 -3 -2  2", "I -1 -1 -3 -3  0 -4 -3  4 -3  2  1 -3 -3 -3 -3 -2 -1  3 -3 -1", "K -1 -3 -1  1 -3 -2 -1 -3  5 -2 -1  0 -1  1  2  0 -1 -2 -3 -2", "L -1 -1 -4 -3  0 -4 -3  2 -2  4  2 -3 -3 -2 -2 -2 -1  1 -2 -1", "M -1 -1 -3 -2  0 -3 -2  1 -1  2  5 -2 -2  0 -1 -1 -1  1 -1 -1", "N -2 -3  1  0 -3  0  1 -3  0 -3 -2  6 -2  0  0  1  0 -3 -4 -2", "P -1 -3 -1 -1 -4 -2 -2 -3 -1 -3 -2 -2  7 -1 -2 -1 -1 -2 -4 -3", "Q -1 -3  0  2 -3 -2  0 -3  1 -2  0  0 -1  5  1  0 -1 -2 -2 -1", "R -1 -3 -2  0 -3 -2  0 -3  2 -2 -1  0 -2  1  5 -1 -1 -3 -3 -2", "S  1 -1  0  0 -2  0 -1 -2  0 -2 -1  1 -1  0 -1  4  1 -2 -3 -2", "T  0 -1 -1 -1 -2 -2 -2 -1 -1 -1 -1  0 -1 -1 -1  1  5  0 -2 -2", "V  0 -1 -3 -2 -1 -3 -3  3 -2  1  1 -3 -2 -2 -3 -2  0  4 -3 -1", "W -3 -2 -4 -3  1 -2 -2 -3 -3 -2 -1 -4 -4 -2 -3 -3 -2 -3 11  2", "Y -2 -2 -3 -2  3 -3  2 -1 -2 -1 -1 -2 -3 -1 -2 -2 -2 -1  2  7")
}