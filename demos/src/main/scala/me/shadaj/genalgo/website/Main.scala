package me.shadaj.genalgo.website

import me.shadaj.genalgo.alignment.GlobalAlignment
import me.shadaj.genalgo.scoring.BLOSUM62

import scala.scalajs.js.JSApp
import org.scalajs.dom.{MouseEvent, document}
import org.scalajs.dom.html
import me.shadaj.genalgo.uniprot.Uniprot
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object Main extends JSApp {
  implicit val alignmentStrategy = GlobalAlignment

  def main = {
    document.getElementById("prefillButton").asInstanceOf[html.Anchor].onclick = (event: MouseEvent) => {
      document.getElementById("uniprot1").asInstanceOf[html.Input].value = "Q9D103"
      document.getElementById("uniprot2").asInstanceOf[html.Input].value = "P13164"
    }
    
    document.getElementById("align-btn").asInstanceOf[html.Button].onclick = (event: MouseEvent) => {
      val uniprot1ID = document.getElementById("uniprot1").asInstanceOf[html.Input].value
      val uniprot2ID = document.getElementById("uniprot2").asInstanceOf[html.Input].value
      Uniprot.getFasta(uniprot1ID).zip(Uniprot.getFasta(uniprot2ID)).foreach { case (uniprot1, uniprot2) =>
        val outputElement = document.getElementById("output")
        outputElement.innerHTML = "Downloaded sequences... now aligning"
        val alignment = uniprot1.sequence.align(uniprot2.sequence, new BLOSUM62(5))
        outputElement.innerHTML =
          s"""Alignment results of ${uniprot1.id}
             |             against ${uniprot2.id}\n""".stripMargin + alignment.toString()
      }
    }
  }
}
