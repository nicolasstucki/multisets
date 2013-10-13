package scala.collection.mutable

import scala.collection.{GenTraversable, mutable}

trait Bag[A]
  extends scala.collection.Bag[A]
  with scala.collection.BagLike[A, mutable.Bag[A]] {

  protected def bktFactory: mutable.BagBucketFactory[A]

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

  def empty[A](implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag.empty(bktFactory)
  }

  def apply[A](elem: (A, Int))(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag(elem)(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag(elem1, elem2, elems: _*)(bktFactory)
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    mutable.DummyMapBag(elems)(bktFactory)
  }

}