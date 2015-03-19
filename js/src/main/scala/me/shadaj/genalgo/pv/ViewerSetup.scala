package me.shadaj.genalgo.pv

import scalajs.js

object ViewerSetup {
  def apply(width: Int = 500, height: Int = 500,
            antialias: Boolean = false, quality: String = "low",
            slabMode: String = "auto", background: String = "white",
            animateTime: Int = 0, fog: Boolean = true) =
    js.Dynamic.literal(width = width, height = height,
                       antialias = antialias, quality = quality,
                       slabMode = slabMode, background = background,
                       animateTime = animateTime, fog = fog)
}
