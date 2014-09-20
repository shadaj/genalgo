package me.shadaj.genalgo.alignment

import me.shadaj.genalgo.sequences.{BioSequence, BaseLike, Indel}
import java.awt.image.BufferedImage
import java.awt.{Color, Graphics2D}
import java.io.File
import javax.imageio.ImageIO

class Alignment[B <: BaseLike, C <: BioSequence[B]](
    val seq1: AlignmentSequence[B],
    val seq2: AlignmentSequence[B],
    val score: Int) {
  override def toString = {
    val comparison = seq1.zip(seq2).map { case (b1, b2) =>
      if (b1.isRight || b2.isRight) {
        " "
      } else if (b1 != b2) {
        ":"
      } else {
        "*"
      }
    }.mkString
    seq1 + "\n" + seq2 + "\n" + comparison
  }

  def toImage(height: Int = 160) = {
    val comparisonWidth = seq1.length
    val image = new BufferedImage(comparisonWidth, height, BufferedImage.TYPE_INT_RGB)
    val graphics = image.getGraphics.asInstanceOf[Graphics2D]
    graphics.setColor(Color.white)
    graphics.fillRect(0, 0, comparisonWidth, height)
    seq1.zip(seq2).zipWithIndex.foreach { case ((b1, b2), index) =>
      if (b1.isRight) {
        graphics.setColor(Color.blue)
        graphics.drawLine(index, 0, index, height/2)
      } else if (b2.isRight) {
        graphics.setColor(Color.blue)
        graphics.drawLine(index, height/2, index, height)
      } else if (b1 != b2) {
        graphics.setColor(Color.red)
        graphics.drawLine(index, 0, index, height)
      } else {
        graphics.setColor(Color.green)
        graphics.drawLine(index, 0, index, height)
      }
    }
    image
  }

  def toImageFile(file: File, height: Int = 160) = {
    ImageIO.write(toImage(height), "png", file)
  }
}