package me.shadaj.genalgo.tests

import utest._

import me.shadaj.genalgo.util.BitStorage

object BitStorageTest extends TestSuite {
  val tests = TestSuite {
    "non-overflowing bits - one-bit data" - {
      for (value <- 0 to 1; size <- 0 to 128) {
        val storage = BitStorage[Int](1, Array.fill(size)(value), identity)
        (0 until size).foreach(i => assert(storage(i, identity) == value))
      }
    }

    "non-overflowing bits - two-bit data" - {
      for (value <- 0 to 3; size <- 0 to 64) {
        val storage = BitStorage[Int](2, Array.fill(size)(value), identity)
        (0 until size).foreach(i => assert(storage(i, identity) == value))
      }
    }

    "non-overflowing bits - three-bit data" - {
      for (value <- 0 to 7; size <- 0 to 32) {
        val storage = BitStorage[Int](3, Array.fill(size)(value), identity)
        (0 until size).foreach(i => assert(storage(i, identity) == value))
      }
    }

    "non-oveflowing bits - five-bit data" - {
      for (value <- 0 to 31; size <- 0 to 16) {
        val storage = BitStorage[Int](5, Array.fill(size)(value), identity)
        (0 until size).foreach(i => assert(storage(i, identity) == value))
      }
    }
  }
}
