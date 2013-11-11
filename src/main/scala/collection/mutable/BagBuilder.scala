package scala.collection.mutable

import scala.collection._

trait BagBuilder[A, +Bag <: collection.Bag[A]] extends mutable.Builder[A, Bag] {
  def add(elem: A, count: Int): this.type

  def addBucket(bucket: collection.BagBucket[A]): this.type
}

object BagBuilder {

  def apply[A, Bag <: collection.Bag[A] with collection.BagLike[A, Bag]](empty: Bag): mutable.BagBuilder[A, Bag] = new BagBuilderImpl(empty)

  private class BagBuilderImpl[A, Bag <: collection.Bag[A] with collection.BagLike[A, Bag]](empty: Bag) extends mutable.BagBuilder[A, Bag] {
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


class GrowingBagBuilder[A, Bag <: collection.mutable.Bag[A] with collection.mutable.BagLike[A, Bag] with generic.GrowableBag[A]](empty: Bag)
  extends mutable.GrowingBuilder[A, Bag](empty)
  with mutable.BagBuilder[A, Bag] {

  def add(elem: A, count: Int): this.type = {
    elems.add(elem, count)
    this
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    elems addBucket bucket
    this
  }
}


