package me.shadaj.genalgo.tests

import org.scalatest.FunSuite
import me.shadaj.genalgo.util.BitStorage

class BitStorageTest extends FunSuite {
  for (value <- 0 to 1; size <- 0 to 128) {
    test(s"non-overflowing bits - one-bit data | value: $value, size: $size") {
      val storage = BitStorage[Int](1, Array.fill(size)(value), identity)
      (0 until size).foreach(i => assert(storage(i, identity) === value))
    }
  }

  for (value <- 0 to 3; size <- 0 to 64) {
    test(s"non-overflowing bits - two-bit data | value: $value, size: $size") {
      val storage = BitStorage[Int](2, Array.fill(size)(value), identity)
      (0 until size).foreach(i => assert(storage(i, identity) === value))
    }
  }
  
  for (value <- 0 to 7; size <- 0 to 32) {
    test(s"non-overflowing bits - three-bit data | value: $value, size: $size") {
      val storage = BitStorage[Int](3, Array.fill(size)(value), identity)
      (0 until size).foreach(i => assert(storage(i, identity) === value, s"Failed for: $i"))
    }
  }
  
  for (value <- 0 to 31; size <- 0 to 16) {
    test(s"non-overflowing bits - five-bit data | value: $value, size: $size") {
      val storage = BitStorage[Int](5, Array.fill(size)(value), identity)
      (0 until size).foreach(i => assert(storage(i, identity) === value, s"Failed for: $i"))
    }
  }
}