package scala.collection.mutable

import scala.collection.{BagBucketFactory, mutable}

trait Bag[A, Bkt <: mutable.BagBucket[A]]
  extends scala.collection.Bag[A, Bkt]
  with scala.collection.BagLike[A, Bkt, mutable.Bag[A, Bkt]] {


  def update(elem: A, count: Int): this.type

  def +=(elem: A): this.type = this += (elem -> 1)

  def +=(elemCount: (A, Int)): this.type = elemCount match {
    case (elem, count) => update(elem, this(elem).multiplicity + count)
  }

  def -=(elem: A): this.type = this -= (elem -> 1)

  def -=(elemCount: (A, Int)): this.type = {
    val (elem, count) = elemCount
    update(elem, Math.max(this(elem).multiplicity - count, 0))
  }


}


object Bag {

  def empty[A, Bkt <: mutable.BagBucket[A]](implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = mutable.DummyMapBag.empty

  def apply[A, Bkt <: mutable.BagBucket[A]](elem: (A, Int))(implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = mutable.DummyMapBag(elem)(m)

  def apply[A, Bkt <: mutable.BagBucket[A]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = mutable.DummyMapBag(elem1, elem2, elems: _*)(m)

  def from[A, Bkt <: mutable.BagBucket[A]](elems: scala.collection.Iterable[A])(implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = mutable.DummyMapBag.from(elems)(m)

}