package scala.collection.mutable

import scala.collection._

class BagBuilder[A, G <: Group[A, G], Coll <: scala.collection.Bag[A, G] with scala.collection.BagLike[A, G, Coll]](empty: Coll) extends mutable.Builder[A, Coll] {
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