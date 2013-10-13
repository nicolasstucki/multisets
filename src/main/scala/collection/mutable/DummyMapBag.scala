package scala.collection.mutable

import scala.collection.{BagBucketFactory, mutable}
import scala.collection

class DummyMapBag[A, Bkt <: mutable.BagBucket[A]](multiplicityMap: mutable.Map[A, Bkt])(implicit protected val m: BagBucketFactory[A, Bkt])
  extends mutable.Bag[A, Bkt]
  with mutable.Builder[A, mutable.DummyMapBag[A, Bkt]] {

  def empty: mutable.DummyMapBag[A, Bkt] = new mutable.DummyMapBag(mutable.Map.empty[A, Bkt])(m)

  def result(): mutable.DummyMapBag[A, Bkt] = new mutable.DummyMapBag(multiplicityMap.clone())

  def clear(): Unit = multiplicityMap.clear()

  override protected[this] def newBuilder: mutable.Builder[A, mutable.DummyMapBag[A, Bkt]] = empty

  // Added elements
  def +(elem: A): mutable.Bag[A, Bkt] = {
    val newMap = mutable.DummyMapBag.from(this.toIterable)
    newMap += elem
    newMap
  }

  // Removed elements
  def -(elem: A): mutable.Bag[A, Bkt] = {
    val newMap = mutable.DummyMapBag.from(this.toIterable)
    newMap -= elem
    newMap
  }

  def bucketsIterator: Iterator[Bkt] = multiplicityMap.valuesIterator

  def update(elem: A, count: Int): this.type = {
    val bkt = multiplicityMap.getOrElseUpdate(elem, m.empty(elem))
    bkt += elem
    this
  }

  //  override def multiplicity(elem: A): Int = multiplicityMap.get(elem) match {
  //    case Some(group) => group.multiplicity
  //    case None => 0
  //  }

  override def distinctIterator = multiplicityMap.keysIterator

  override def multiplicitiesIterator = multiplicityMap.iterator map {
    case (elem, group) => elem -> group.multiplicity
  }

  override def iterator = multiplicityMap.valuesIterator.flatMap(_.iterator)

}


object DummyMapBag {

  def empty[A, Bkt <: mutable.BagBucket[A]](implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = new mutable.DummyMapBag[A, Bkt](mutable.Map.empty[A, Bkt])

  def apply[A, Bkt <: mutable.BagBucket[A]](elemCount: (A, Int))(implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = new mutable.DummyMapBag[A, Bkt](mutable.Map[A, Bkt](elemCount._1 -> m(elemCount)))

  def apply[A, Bkt <: mutable.BagBucket[A]](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = {
    new mutable.DummyMapBag[A, Bkt](mutable.Map[A, Bkt]() + (elem1._1 -> m(elem1)) + (elem2._1 -> m(elem2)) ++ elems.map((elemCount: (A, Int)) => elemCount._1 -> m(elemCount)))
  }

  def from[A, Bkt <: mutable.BagBucket[A]](elems: mutable.Iterable[(A, Int)])(implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = {
    new mutable.DummyMapBag[A, Bkt](mutable.Map[A, Bkt]() ++ elems.map((elemCount: (A, Int)) => elemCount._1 -> m(elemCount)))
  }

  def from[A, Bkt <: mutable.BagBucket[A]](elems: scala.collection.Iterable[A])(implicit m: BagBucketFactory[A, Bkt]): mutable.Bag[A, Bkt] = new mutable.DummyMapBag[A, Bkt](mutable.Map() ++ (elems map (elem => elem -> m(elem -> elems.count(elem == _)))))
}