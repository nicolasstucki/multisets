package scala.collection.mutable

import scala.collection.{mutable, generic}
import scala.collection

class BagGrowingBuilder[A, Bag <: collection.mutable.Bag[A] with collection.mutable.BagLike[A, Bag] with generic.GrowableBag[A]](empty: Bag)
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
