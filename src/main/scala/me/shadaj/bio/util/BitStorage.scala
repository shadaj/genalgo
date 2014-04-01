package me.shadaj.bio.util

class BitStorage(bitsPerGroup: Int, data: Array[Int]) {
  import BitStorage._
  
  private val storage = Array.fill(math.ceil((data.length * bitsPerGroup)/BitsPerInt.toFloat).toInt)(0)
  
  private def calcIndices(index: Int) = {
    val bitIndex = index * bitsPerGroup
    val storageIndex = bitIndex / BitsPerInt
    val indexInStorage = bitIndex % BitsPerInt
    val overflow = indexInStorage + bitsPerGroup - BitsPerInt
    (storageIndex, indexInStorage, overflow)
  }
  
  data.zipWithIndex.foreach { case (d, i) =>
    val (storageIndex, indexInStorage, overflow) = calcIndices(i)
    storage(storageIndex) |= (d << indexInStorage)
    if (overflow > 0) {
      storage(storageIndex + 1) |= (d >>> (bitsPerGroup - overflow))
    }
  }
  
  def apply(loc: Int): Int = {
    val (storageIndex, indexInStorage, overflow) = calcIndices(loc)
    val bitmask = ((1 << bitsPerGroup) - 1) << indexInStorage
    val withoutOverflow = (storage(storageIndex) & bitmask) >>> indexInStorage
    if (overflow <= 0) {
      withoutOverflow
    } else {
      val overflowed = storage(storageIndex + 1) & ((1 << overflow) - 1)
      withoutOverflow | (overflowed << (bitsPerGroup - overflow))
    }
  }
  
  def apply[T](loc: Int, f: Int => T): T = {
    f(apply(loc))
  }
}

object BitStorage {
  val BitsPerInt = 32
  
  def apply[T](bitsPerGroup: Int, data: Array[T], f: T => Int): BitStorage = {
    new BitStorage(bitsPerGroup, data.map(f))
  }
}