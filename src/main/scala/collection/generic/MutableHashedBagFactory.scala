package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class MutableHashedBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends HashedBagFactory[CC] {

  type BB[X] = mutable.BagBucket[X]
  type BBC[X] = mutable.HashedBagBucketConfiguration[X]


  def newBuilder[A](implicit bucketFactory: BBC[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder(empty)
}