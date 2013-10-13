package scala.collection.mutable

import scala.collection._

class BagBuilder[A, Bkt <: scala.collection.BagBucket[A], Coll <: scala.collection.Bag[A, Bkt] with scala.collection.BagLike[A, Bkt, Coll]](empty: Coll) extends mutable.Builder[A, Coll] {
  protected var elems = empty

  def +=(x: A): this.type = {
    elems = elems + x
    this
  }

  def clear() {
    elems = empty
  }

  def result(): Coll = elems

}