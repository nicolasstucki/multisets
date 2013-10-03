package scala.collection

import scala.collection.mutable.{Builder, BagBuilder}

import scala.collection.generic.Subtractable

trait BagLike[A, G <: Group[A, G], +This <: BagLike[A, G, This] with Bag[A, G]]
  extends IterableLike[A, This]
  with Subtractable[A, This] {
  self =>


  def empty: This

  override protected[this] def newBuilder: mutable.Builder[A, This] = new mutable.BagBuilder[A, G, This](empty)


  def multiplicity(elem: A): Int = this count (elem == _)

  def contains(elem: A): Boolean = multiplicity(elem) > 0


  // Added elements
  def +(elem: A): This

  def +(elemCount: (A, Int)): This = elemCount match {
    case (elem, count) if count > 0 => (this + elem) + (elem -> (count - 1))
    case _ => repr
  }

  def ++(elems: GenTraversableOnce[A]): This = (repr /: elems)(_ + _)

  // Removed elements
  def -(elem: A): This

  def -(elemCount: (A, Int)): This = elemCount match {
    case (elem, count) if count > 0 => (this - elem) - (elem -> (count - 1))
    case _ => repr
  }

  def -*(elem: A): This = this - (elem -> this.multiplicity(elem))

}
