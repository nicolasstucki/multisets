package scala.collection.mutable

import scala.collection._
import scala.collection

trait BagBucketBuilder[A, +BagBucket <: collection.BagBucket[A]] extends mutable.Builder[A, BagBucket] {

  def add(elem: A, count: Int): this.type

  def addBucket(bucket: collection.BagBucket[A]): this.type
}

object BagBucketBuilder {

  def apply[A](empty: immutable.BagBucket[A]): mutable.BagBucketBuilder[A, immutable.BagBucket[A]] = new BagBucketBuilderImpl[A](empty)

  private class BagBucketBuilderImpl[A](empty: immutable.BagBucket[A]) extends mutable.BagBucketBuilder[A, immutable.BagBucket[A]] {
    protected var elems = empty

    def +=(elem: A): this.type = {
      elems = elems + elem
      this
    }

    def clear(): Unit = elems = empty

    def result(): immutable.BagBucket[A] = elems

    def add(elem: A, count: Int): this.type = {
      elems = elems.added(elem, count)
      this
    }

    def addBucket(bucket: collection.BagBucket[A]): this.type = {
      elems = elems addedBucket bucket
      this
    }
  }

}

class GrowingBagBucketBuilder[A](empty: mutable.BagBucket[A])
  extends mutable.GrowingBuilder[A, mutable.BagBucket[A]](empty)
  with mutable.BagBucketBuilder[A, mutable.BagBucket[A]] {

  def add(elem: A, count: Int): this.type = {
    elems.add(elem, count)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    elems addBucket bucket
    this
  }
}


