package scala.collection.mutable

import scala.collection._

class BagBuilder[A, Coll <: scala.collection.Bag[A] with scala.collection.BagLike[A, Coll]](empty: Coll) extends mutable.Builder[A, Coll] {
  protected var elems = empty

  def +=(x: A): this.type = {
    elems = elems + x
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]): this.type = {
    elems = elems addedBucket bucket
    this
  }

  def clear() {
    elems = empty
  }

  def result(): Coll = elems

}