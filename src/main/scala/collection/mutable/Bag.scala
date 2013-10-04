package scala.collection.mutable

import scala.collection.{mutable, BagBuckets, BagBucket}

trait Bag[A, G <: BagBucket[A, G]] extends scala.collection.Bag[A, G] {


  def update(elem: A, count: Int): Unit

  def +=(elem: A): Unit = this += (elem -> 1)

  def +=(elemCount: (A, Int)): Unit = elemCount match {
    case (elem, count) => update(elem, (this multiplicity elem) + count)
  }

  def -=(elem: A): Unit = this -= (elem -> 1)

  def -=(elemCount: (A, Int)): Unit = {
    val (elem, count) = elemCount
    update(elem, Math.max((this multiplicity elem) - count, 0))
  }


}


object Bag {

  def empty[A, G <: BagBucket[A, G]](implicit m: BagBuckets[A, G]): mutable.Bag[A, G] = mutable.MapBag(m)

  def apply[A, G <: BagBucket[A, G]](implicit m: BagBuckets[A, G]): mutable.Bag[A, G] = empty(m)

  def apply[A, G <: BagBucket[A, G]](elem: (A, Int))(implicit m: BagBuckets[A, G]): mutable.Bag[A, G] = mutable.MapBag(elem)(m)

  def apply[A, G <: BagBucket[A, G]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: BagBuckets[A, G]): mutable.Bag[A, G] = mutable.MapBag(elem1, elem2, elems: _*)(m)

  def apply[A, G <: BagBucket[A, G]](elems: scala.collection.Iterable[A])(implicit m: BagBuckets[A, G]): mutable.Bag[A, G] = mutable.MapBag(elems)(m)

}