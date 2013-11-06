package scala.collection.mutable

import scala.collection._

trait BagBucketBuilder[A, +BagBucket <: collection.BagBucket[A]] extends mutable.Builder[A, BagBucket] {

  def add(elem: A, count: Int): this.type

  def addBucket(bucket: collection.BagBucket[A]): this.type
}

class BagBucketBuilderImpl[A](empty: immutable.BagBucket[A]) extends mutable.BagBucketBuilder[A, immutable.BagBucket[A]] {
  protected var elems = empty

  def +=(x: A): this.type = {
    elems = elems + x
    this
  }

  def add(elem: A, count: Int): this.type = {
    elems = elems.added(elem, count)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    elems = elems addedBucket bucket
    this
  }

  def clear() {
    elems = empty
  }

  def result() = elems
}