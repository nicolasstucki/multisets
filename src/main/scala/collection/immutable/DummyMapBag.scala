package scala.collection.immutable

import scala.collection.{GenTraversable, immutable}


class DummyMapBag[A](multiplicityMap: immutable.Map[A, immutable.BagBucket[A]])(protected val bktFactory: BagBucketFactory[A])
  extends immutable.Bag[A] {

  def empty: DummyMapBag[A] = DummyMapBag.empty(bktFactory)

  def bucketsIterator: Iterator[BagBucket[A]] = multiplicityMap.valuesIterator

  // Added elements
  def +(elem: A): DummyMapBag[A] = {
    val bkt = multiplicityMap.getOrElse(elem, bktFactory.empty(elem)) + elem
    new DummyMapBag(multiplicityMap.updated(elem, bkt))(bktFactory)
  }

  // Removed elements
  def -(elem: A): DummyMapBag[A] = {
    val bkt = multiplicityMap.getOrElse(elem, bktFactory.empty(elem)) - elem
    if (bkt.isEmpty) {
      new DummyMapBag(multiplicityMap - elem)(bktFactory)
    } else {
      new DummyMapBag(multiplicityMap.updated(elem, bkt))(bktFactory)
    }
  }

}


object DummyMapBag {

  def empty[A](implicit bktFactory: immutable.BagBucketFactory[A]): immutable.DummyMapBag[A] = {
    new immutable.DummyMapBag[A](immutable.Map.empty[A, BagBucket[A]])(bktFactory)
  }

  def apply[A](elemCount: (A, Int))(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.DummyMapBag[A] = {
    new immutable.DummyMapBag[A](immutable.Map(elemCount._1 -> bktFactory(elemCount)))(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    var bag = this(elem1) + elem2
    for (elem <- elems) {
      bag = bag + elem
    }
    bag
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: immutable.BagBucketFactory[A] = immutable.MultiplicityBagBucketFactory.of[A]): immutable.DummyMapBag[A] = {
    new immutable.DummyMapBag[A](immutable.Map() ++ (elems map (elem => elem -> bktFactory(elem -> elems.count(elem == _)))))(bktFactory)
  }
}