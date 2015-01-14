package me.shadaj.genalgo.util

class BitStorage private(bitsPerGroup: Int, storage: Array[Int]) extends Serializable {
  import BitStorage._

  def complement = {
    new BitStorage(bitsPerGroup, storage.map(~_))
  }
  
  private def apply(loc: Int): Int = {
    val (storageIndex, indexInStorage, overflow) = calcIndices(loc, bitsPerGroup)
    val bitmask = ((1 << bitsPerGroup) - 1) << indexInStorage
    val withoutOverflow = (storage(storageIndex) & bitmask) >>> indexInStorage
    if (overflow <= 0) {
      withoutOverflow
    } else {
      val overflowed = storage(storageIndex + 1) & ((1 << overflow) - 1)
      withoutOverflow | (overflowed << (bitsPerGroup - overflow))
    }
  }

  private def update(loc: Int, value: Int): Unit = {
    val (storageIndex, indexInStorage, overflow) = calcIndices(loc, bitsPerGroup)
    storage(storageIndex) |= (value << indexInStorage)
    if (overflow > 0) {
      storage(storageIndex + 1) |= (value >>> (bitsPerGroup - overflow))
    }
  }
  
  def apply[T](loc: Int, f: Int => T): T = {
    f(apply(loc))
  }

  def update[T](loc: Int, f: T => Int, value: T): Unit = {
    update(loc, f(value))
  }
}

object BitStorage {
  val BitsPerInt = 32

  private def calcIndices(index: Int, bitsPerGroup: Int) = {
    val bitIndex = index * bitsPerGroup
    val storageIndex = bitIndex / BitsPerInt
    val indexInStorage = bitIndex % BitsPerInt
    val overflow = indexInStorage + bitsPerGroup - BitsPerInt
    (storageIndex, indexInStorage, overflow)
  }

  private def apply(bitsPerGroup: Int, data: Array[Int]) = {
    val storage = Array.fill(math.ceil((data.length * bitsPerGroup)/BitsPerInt.toFloat).toInt)(0)
    data.zipWithIndex.foreach { case (d, i) =>
      val (storageIndex, indexInStorage, overflow) = calcIndices(i, bitsPerGroup)
      storage(storageIndex) |= (d << indexInStorage)
      if (overflow > 0) {
        storage(storageIndex + 1) |= (d >>> (bitsPerGroup - overflow))
      }
    }

    new BitStorage(bitsPerGroup, storage)
  }

  def apply[T](bitsPerGroup: Int, data: Array[T], f: T => Int): BitStorage = {
    BitStorage(bitsPerGroup, data.map(f))
  }
}