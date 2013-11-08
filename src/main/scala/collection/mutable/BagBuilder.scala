package scala.collection.mutable

import scala.collection._

trait BagBuilder[A, +Bag <: collection.Bag[A] with collection.BagLike[A, Bag]] extends mutable.Builder[A, Bag] {
  def add(elem: A, count: Int): this.type

  def addBucket(bucket: collection.BagBucket[A]): this.type
}

object BagBuilder {

  def newBuilder[A, Bag <: collection.Bag[A] with collection.BagLike[A, Bag]](empty: Bag): mutable.BagBuilder[A, Bag] = new BagBuilderImpl(empty)

  protected class BagBuilderImpl[A, Bag <: collection.Bag[A] with collection.BagLike[A, Bag]](empty: Bag) extends mutable.BagBuilder[A, Bag] {
    protected var elems = empty

    def +=(x: A) = {
      elems = elems + x
      this
    }

    def add(elem: A, count: Int) = {
      elems = elems.added(elem, count)
      this
    }

    def addBucket(bucket: collection.BagBucket[A]) = {
      elems = elems addedBucket bucket
      this
    }

    def clear() {
      elems = empty
    }

    def result() = elems
  }

}


