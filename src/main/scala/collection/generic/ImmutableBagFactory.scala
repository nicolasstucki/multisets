package scala.collection.generic

import scala.collection.{mutable, immutable}
import scala.language.higherKinds

abstract class ImmutableBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]], BC[X] <: immutable.BagConfiguration[X]]
  extends BagFactory[CC, immutable.BagBucket, BC] {

  def newBuilder[A](implicit bagConfiguration: BC[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}

abstract class ImmutableHashedBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]]]
  extends ImmutableBagFactory[CC, immutable.HashedBagConfiguration] {

  def configuration = immutable.HashedBagConfiguration

}

abstract class ImmutableSortedBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]]]
  extends ImmutableBagFactory[CC, immutable.SortedBagConfiguration] {

  def configuration = immutable.SortedBagConfiguration

}