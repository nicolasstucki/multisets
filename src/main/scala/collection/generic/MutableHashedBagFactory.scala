package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class MutableHashedBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends HashedBagFactory[CC] {

  type BagBucket[X] = mutable.BagBucket[X]
  type BagBucketFactory[X] = mutable.HashedBagBucketFactory[X]


  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder(empty)
}