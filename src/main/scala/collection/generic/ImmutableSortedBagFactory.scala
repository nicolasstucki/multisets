package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class ImmutableSortedBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]]]
  extends SortedBagFactory[CC] {

  type BB[X] = immutable.BagBucket[X]
  type BBC[X] = immutable.SortedBagBucketConfiguration[X]

  def newBuilder[A](implicit bagBucketConfiguration: BBC[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}