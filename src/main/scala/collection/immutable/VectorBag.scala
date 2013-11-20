package scala.collection.immutable

import scala.collection._
import scala.collection
import scala.collection.generic.CanBuildFrom

final class VectorBag[A](vector: Vector[BagBucket[A]])(implicit protected val bucketFactory: immutable.BagBucketFactory[A])
  extends immutable.Bag[A]
  with immutable.BagLike[A, VectorBag[A]] {


  def addedBucket(bucket: collection.BagBucket[A]): VectorBag[A] = {
    val bb = bucketFactory.newBuilder(bucket.sentinel)
    bb addBucket bucket
    for (i <- 0 until vector.size) {
      val bucket2 = vector(i)
      if (bucketFactory.equiv(bucket.sentinel, bucket2.sentinel)) {
        bb addBucket bucket2
        return new VectorBag[A](vector.updateAt(i, bb.result()))
      }
    }

    new VectorBag[A](vector.appendBack(bb.result()))
  }

  def bucketsIterator: Iterator[BagBucket[A]] = vector.iterator

  def empty: VectorBag[A] = VectorBag.empty[A]

}

object VectorBag extends generic.ImmutableBagFactory[VectorBag] {

  implicit def canBuildFrom[A](implicit bucketFactory: BagBucketFactory[A]): CanBuildFrom[Coll, A, Bag[A]] = bagCanBuildFrom[A]


  def empty[A](implicit bucketFactory: BagBucketFactory[A]): VectorBag[A] = new VectorBag(Vector.empty[BagBucket[A]])
}
