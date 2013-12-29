package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class ImmutableHashedBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]]]
  extends HashedBagFactory[CC] {

  type BB[X] = immutable.BagBucket[X]
  type BBC[X] = immutable.HashedBagBucketConfiguration[X]

  def newBuilder[A](implicit bucketFactory: BBC[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}