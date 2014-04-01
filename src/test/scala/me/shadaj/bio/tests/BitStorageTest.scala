package me.shadaj.bio.tests

import org.scalatest.FunSuite
import me.shadaj.bio.uniprot.Uniprot
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration._
import me.shadaj.bio.util.BitStorage

class BitStorageTest extends FunSuite {
  for (value <- 0 to 1; size <- 0 to 128) {
    test(s"non-overflowing bits - one-bit data | value: $value, size: $size") {
      val storage = new BitStorage(1, Array.fill(size)(value))
      (0 until size).foreach(i => assert(storage(i) === value))
    }
  }

  for (value <- 0 to 3; size <- 0 to 64) {
    test(s"non-overflowing bits - two-bit data | value: $value, size: $size") {
      val storage = new BitStorage(2, Array.fill(size)(value))
      (0 until size).foreach(i => assert(storage(i) === value))
    }
  }
  
  for (value <- 0 to 7; size <- 0 to 32) {
    test(s"non-overflowing bits - three-bit data | value: $value, size: $size") {
      val storage = new BitStorage(3, Array.fill(size)(value))
      (0 until size).foreach(i => assert(storage(i) === value, s"Failed for: $i"))
    }
  }
  
  for (value <- 0 to 31; size <- 0 to 16) {
    test(s"non-overflowing bits - five-bit data | value: $value, size: $size") {
      val storage = new BitStorage(5, Array.fill(size)(value))
      (0 until size).foreach(i => assert(storage(i) === value, s"Failed for: $i"))
    }
  }
}