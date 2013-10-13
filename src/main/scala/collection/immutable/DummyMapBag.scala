package scala.collection.immutable

import scala.collection.{immutable, BagBucketFactory}
import scala.collection


class DummyMapBag[A, Bkt <: immutable.BagBucket[A, Bkt]](multiplicityMap: immutable.Map[A, Bkt])(implicit protected val m: BagBucketFactory[A, Bkt])
  extends immutable.Bag[A, Bkt] {

  def empty: collection.Bag[A, Bkt] = new DummyMapBag(Map.empty)

  def bucketsIterator: Iterator[Bkt] = multiplicityMap.valuesIterator

  // Added elements
  def +(elem: A): DummyMapBag[A, Bkt] = {
    val bkt = multiplicityMap.getOrElse(elem, m.empty(elem)) + elem
    new DummyMapBag(multiplicityMap.updated(elem, bkt))
  }

  // Removed elements
  def -(elem: A): DummyMapBag[A, Bkt] = {
    val bkt = multiplicityMap.getOrElse(elem, m.empty(elem)) - elem
    if (bkt.isEmpty) {
      new DummyMapBag(multiplicityMap - elem)
    } else {
      new DummyMapBag(multiplicityMap.updated(elem, bkt))
    }
  }
}


object DummyMapBag {

  def empty[A, Bkt <: immutable.BagBucket[A, Bkt]](implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = new immutable.DummyMapBag[A, Bkt](immutable.Map.empty[A, Bkt])

  def apply[A, Bkt <: immutable.BagBucket[A, Bkt]](elemCount: (A, Int))(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = new immutable.DummyMapBag[A, Bkt](immutable.Map[A, Bkt](elemCount._1 -> m(elemCount)))

  def apply[A, Bkt <: immutable.BagBucket[A, Bkt]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = {
    new immutable.DummyMapBag[A, Bkt](immutable.Map[A, Bkt]() + (elem1._1 -> m(elem1)) + (elem2._1 -> m(elem2)) ++ elems.map((elemCount: (A, Int)) => elemCount._1 -> m(elemCount)))
  }

  def from[A, Bkt <: immutable.BagBucket[A, Bkt]](elems: immutable.Iterable[(A, Int)])(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = {
    new immutable.DummyMapBag[A, Bkt](immutable.Map[A, Bkt]() ++ elems.map((elemCount: (A, Int)) => elemCount._1 -> m(elemCount)))
  }

  def from[A, Bkt <: immutable.BagBucket[A, Bkt]](elems: scala.collection.Iterable[A])(implicit m: BagBucketFactory[A, Bkt]): immutable.Bag[A, Bkt] = new immutable.DummyMapBag[A, Bkt](immutable.Map() ++ (elems map (elem => elem -> m(elem -> elems.count(elem == _)))))
}