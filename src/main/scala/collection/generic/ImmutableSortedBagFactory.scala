package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class ImmutableSortedBagFactory[CC[X] <: immutable.SortedBag[X] with immutable.SortedBagLike[X, CC[X]]]
  extends SortedBagFactory[CC] {

  type BagBucket[X] = immutable.BagBucket[X]
  type BagBucketFactory[X] = immutable.SortedBagBucketFactory[X]

  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}