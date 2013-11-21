package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class MutableSortedBagFactory[CC[X] <: mutable.SortedBag[X] with mutable.SortedBagLike[X, CC[X]]]
  extends SortedBagFactory[CC] {

  type BagBucket[X] = mutable.BagBucket[X]
  type BagBucketFactory[X] = mutable.SortedBagBucketFactory[X]


  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder(empty)
}