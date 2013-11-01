package scala.collection.mutable

import scala.collection.{mutable, GenTraversable}
import scala.collection

class DummyMapBag[A](multiplicityMap: mutable.Map[A, mutable.BagBucket[A]])(protected val bucketFactory: mutable.BagBucketFactory[A])
  extends mutable.Bag[A]
  with mutable.Builder[A, mutable.DummyMapBag[A]] {

  def empty: mutable.DummyMapBag[A] = mutable.DummyMapBag.empty[A](bucketFactory)

  def result(): mutable.DummyMapBag[A] = new mutable.DummyMapBag(multiplicityMap.clone())(bucketFactory)

  def clear(): Unit = multiplicityMap.clear()

  override protected[this] def newBuilder: mutable.Builder[A, mutable.DummyMapBag[A]] = empty

  // Added elements
  def +(elem: A): mutable.Bag[A] = {
    val newBag = mutable.DummyMapBag(this.toIterable)(bucketFactory)
    newBag += elem
    newBag
  }


  def updateBucket(bucket: mutable.BagBucket[A]) = {
    multiplicityMap.update(bucket.sentinel, bucket)
    this
  }

  // Added Bucket
  def addedBucket(bucket: collection.BagBucket[A]) = {
    val newBag = mutable.DummyMapBag(this.toIterable)(bucketFactory)
    newBag addBucket bucket
    newBag
  }

  // Removed elements
  def -(elem: A): mutable.Bag[A] = {
    val newBag = mutable.DummyMapBag(this.toIterable)(bucketFactory)
    newBag -= elem
    newBag
  }

  def bucketsIterator: Iterator[mutable.BagBucket[A]] = multiplicityMap.valuesIterator

  def update(elem: A, count: Int): this.type = {
    val bkt = multiplicityMap.getOrElseUpdate(elem, bucketFactory.empty(elem))
    bkt += elem
    this
  }

  override def distinctIterator = multiplicityMap.keysIterator

  override def multiplicitiesIterator = multiplicityMap.iterator map {
    case (elem, group) => elem -> group.multiplicity
  }

  override def iterator = multiplicityMap.valuesIterator.flatMap(_.iterator)

}


object DummyMapBag {

  def empty[A](implicit bktFactory: mutable.BagBucketFactory[A]): mutable.DummyMapBag[A] = {
    new mutable.DummyMapBag[A](mutable.Map.empty[A, mutable.BagBucket[A]])(bktFactory)
  }

  def apply[A](elemCount: (A, Int))(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.DummyMapBag[A] = {
    val (elem, count) = elemCount
    new mutable.DummyMapBag[A](mutable.Map(elemCount._1 -> bktFactory.from(elem, count)))(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.DummyMapBag[A] = {
    val bag = this(elem1) += elem2
    for (elem <- elems) {
      bag += elem
    }
    bag
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: mutable.BagBucketFactory[A]): mutable.Bag[A] = {
    new mutable.DummyMapBag[A](mutable.Map() ++ (elems map (elem => elem -> bktFactory.from(elem, elems.count(elem == _)))))(bktFactory)
  }
}