package scala.collection.mutable

import scala.collection.{Iterator, GenTraversable, mutable}
import scala.collection

trait Bag[A]
  extends collection.Bag[A]
  with mutable.BagLike[A, mutable.Bag[A]] {

  protected override type BagBucket = mutable.BagBucket[A]
  protected override type BagBucketFactory = mutable.BagBucketFactory[A]


  def update(elem: A, count: Int): this.type

  def updateBucket(bucket: mutable.BagBucket[A]): this.type

  def bucketsIterator: Iterator[BagBucket]


  override def getBucket(elem: A): Option[BagBucket] = bucketsIterator.find(_.sentinel == elem)

  def +=(elem: A): this.type = this += (elem -> 1)

  def +=(elemCount: (A, Int)): this.type = elemCount match {
    case (elem, count) => update(elem, this(elem).multiplicity + count)
  }

  def addBucket(bucket: collection.BagBucket[A]): this.type = {
    this.getBucket(bucket.sentinel) match {
      case Some(b) => b addBucket bucket
      case None => updateBucket((bucketFactory.newBuilder(bucket.sentinel) addBucket bucket).result())
    }
    this
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