package scala.collection
package immutable

import scala.collection.{BagLike, immutable, BagBucketFactory}

trait Bag[A, Bkt <: immutable.BagBucket[A, Bkt]]
  extends scala.collection.Bag[A, Bkt]
  with BagLike[A, Bkt, Bag[A, Bkt]] {


}


object Bag {

  def empty[A, Bkt <: immutable.BagBucket[A, Bkt]](implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = immutable.DummyMapBag.empty

  def apply[A, Bkt <: immutable.BagBucket[A, Bkt]](elem: (A, Int))(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = immutable.DummyMapBag(elem)

  def apply[A, Bkt <: immutable.BagBucket[A, Bkt]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = immutable.DummyMapBag(elem1, elem2, elems: _*)

  def from[A, Bkt <: immutable.BagBucket[A, Bkt]](elems: scala.collection.Iterable[A])(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = immutable.DummyMapBag.from(elems)

}