package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class ImmutableHashedBagFactory[CC[X] <: immutable.HashedBag[X] with immutable.HashedBagLike[X, CC[X]]]
  extends HashedBagFactory[CC] {

  type BagBucket[X] = immutable.BagBucket[X]
  type BagBucketFactory[X] = immutable.HashedBagBucketFactory[X]

  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}