package scala.collection.immutable

import scala.collection._
import scala.collection

final class VectorBag[A](vector: Vector[BagBucket[A]])(protected val bucketFactory: immutable.BagBucketFactory[A])
  extends immutable.Bag[A]
  with immutable.BagLike[A, VectorBag[A]] {


  def addedBucket(bucket: collection.BagBucket[A]): VectorBag[A] = {
    val bb = bucketFactory.newBuilder(bucket.sentinel)
    bb addBucket bucket
    for (i <- 0 until vector.size) {
      val bucket2 = vector(i)
      if (bucketFactory.equiv(bucket.sentinel, bucket2.sentinel)) {
        bb addBucket bucket2
        return new VectorBag[A](vector.updateAt(i, bb.result()))(bucketFactory)
      }
    }

    new VectorBag[A](vector.appendBack(bb.result()))(bucketFactory)
  }

  def bucketsIterator: Iterator[BagBucket[A]] = vector.iterator

  def empty: VectorBag[A] = new VectorBag[A](Vector.empty[BagBucket[A]])(bucketFactory)

}

object VectorBag extends generic.ImmutableBagFactory[VectorBag] {
  def empty[A](implicit bucketFactory: VectorBag.BagBucketFactory[A]): VectorBag[A] = new VectorBag(Vector.empty[BagBucket[A]])(bucketFactory)
}
