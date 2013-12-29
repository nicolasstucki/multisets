package scala.collection.generic

import scala.language.higherKinds
import scala.collection._

abstract class MutableSortedBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends SortedBagFactory[CC] {

  type BB[X] = mutable.BagBucket[X]
  type BBC[X] = mutable.SortedBagBucketConfiguration[X]


  def newBuilder[A](implicit bagBucketConfiguration: BBC[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder(empty)
}