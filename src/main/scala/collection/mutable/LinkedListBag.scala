package scala.collection.mutable

import scala.collection.generic.{CanBuildFrom, MutableBagFactory}
import scala.collection.mutable
import scala.collection

final class LinkedListBag[A](list: mutable.MutableList[mutable.BagBucket[A]])(implicit protected val bucketFactory: mutable.BagBucketFactory[A])
  extends mutable.Bag[A] {
  //with mutable.BagLike[A, LinkedListBag[A]] {

  def clear(): Unit = list.clear()

  def bucketsIterator: Iterator[BagBucket[A]] = list.iterator

  def empty: mutable.LinkedListBag[A] = mutable.LinkedListBag.empty

  def addedBucket(bucket: collection.BagBucket[A]): mutable.LinkedListBag[A] = {
    val newList = mutable.MutableList.empty[BagBucket[A]]
    var added = false
    for (bucket2 <- bucketsIterator) {
      val bb = bucketFactory.newBuilder(bucket2.sentinel)
      bb addBucket bucket2
      if (!added && bucketFactory.equiv(bucket.sentinel, bucket2.sentinel)) {
        bb addBucket bucket
        added = true
      }
      newList += bb.result()
    }

    if (!added) {
      val bb = bucketFactory.newBuilder(bucket.sentinel)
      bb addBucket bucket
      newList += bb.result()
    }

    new mutable.LinkedListBag(newList)
  }

  def updateBucket(bucket: mutable.BagBucket[A]): this.type = {
    val index = list.indexWhere(b => bucketFactory.equiv(b.sentinel, bucket.sentinel))
    if (index >= 0) {
      list.update(index, bucket)
    } else {
      list += bucket
    }
    this
  }
}


object LinkedListBag extends MutableBagFactory[mutable.Bag] {

  implicit def canBuildFrom[A](implicit bucketFactory: BagBucketFactory[A]): CanBuildFrom[Coll, A, mutable.Bag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bucketFactory: BagBucketFactory[A]): mutable.LinkedListBag[A] = new mutable.LinkedListBag[A](mutable.MutableList.empty[BagBucket[A]])
}