package scala.collection.mutable

import scala.collection._

class BagBuilder[A, Coll <: scala.collection.Bag[A] with scala.collection.BagLike[A, Coll]](empty: Coll) extends Builder[A, Coll] {
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