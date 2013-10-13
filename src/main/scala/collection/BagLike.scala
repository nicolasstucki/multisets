package scala.collection


import scala.collection.generic.Subtractable

trait BagLike[A, G <: BagBucket[A], +This <: BagLike[A, G, This] with Bag[A, G]]
  extends IterableLike[A, This]
  with Subtractable[A, This] {
  self =>


  def empty: This

  override protected[this] def newBuilder: mutable.Builder[A, This] = new mutable.BagBuilder[A, G, This](empty)

  def bucketsIterator: Iterator[G]


  //  def multiplicity(elem: A): Int

  def contains(elem: A): Boolean = repr(elem).multiplicity > 0


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

  def -*(elem: A): This = this - (elem -> repr(elem).multiplicity)


  def setMultiplicity(elem: A, count: Int): This = this -* elem + (elem -> count)
}
