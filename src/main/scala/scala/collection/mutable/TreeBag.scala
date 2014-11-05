package scala.collection.mutable

import scala.collection.{mutable, immutable, generic}
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.SortedBagConfiguration._
import scala.collection


// This implementation is a dummy. Need Scala 2.11 to have access to immutable.RedBlackTree from mutable package.
class TreeBag[A](initialContents: immutable.TreeBag[A])(implicit protected val bagConfiguration: mutable.SortedBagConfiguration[A], val immutableBagConfiguration: immutable.SortedBagConfiguration[A])
  extends mutable.Bag[A]
  with mutable.BagLike[A, mutable.TreeBag[A]] {

  var contents: immutable.TreeBag[A] = initialContents

  def clear(): Unit = contents = immutable.TreeBag.empty[A]

  def empty: mutable.TreeBag[A] = new mutable.TreeBag(immutable.TreeBag.empty[A])

  protected def updatedBucket(bucket: BagBucket): mutable.TreeBag[A] = new mutable.TreeBag(contents.updatedBucket(immutableBagConfiguration.bucketFrom(bucket)))

  def bucketsIterator: Iterator[BagBucket] = contents.bucketsIterator.map(bagConfiguration.bucketFrom(_))

  override def addedBucket(bucket: collection.BagBucket[A]): mutable.TreeBag[A] = new mutable.TreeBag(contents.addedBucket(immutableBagConfiguration.bucketFrom(bucket)))

  override def getBucket(elem: A): Option[mutable.TreeBag[A]#BagBucket] = contents.getBucket(elem).map(bagConfiguration.bucketFrom)

  protected def updateBucket(bucket: mutable.BagBucket[A]): this.type = {
    contents = contents.updatedBucket(immutableBagConfiguration.bucketFrom(bucket))
    this
  }
}

object TreeBag extends generic.MutableSortedBagFactory[mutable.TreeBag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: mutable.SortedBagConfiguration[A]): CanBuildFrom[Coll, A, mutable.TreeBag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: mutable.SortedBagConfiguration[A]): mutable.TreeBag[A] = {
    implicit val config = bagConfiguration match {
      case conf: SortedBagOfMultiplicitiesBagBucketConfiguration[A] => immutable.TreeBag.configuration.compactWithEquiv[A](conf.equivClass)(conf.innerOrdering)
      case conf: SortedVectorBagConfiguration[A] => immutable.TreeBag.configuration.keepAll[A](conf.equivClass)
      case conf => immutable.TreeBag.configuration.compact[A](conf.equivClass)
    }
    new mutable.TreeBag(immutable.TreeBag.empty[A](config))
  }

}
