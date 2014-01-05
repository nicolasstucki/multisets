package scala.collection.generic

import scala.language.higherKinds
import scala.collection._


abstract class MutableBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]], BC[X] <: mutable.BagConfiguration[X]]
  extends BagFactory[CC, mutable.BagBucket, BC] {

  def newBuilder[A](implicit bagConfiguration: BC[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder(empty)
}

abstract class MutableHashedBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends MutableBagFactory[CC, mutable.HashedBagConfiguration] {

  def configuration = mutable.HashedBagConfiguration

}


abstract class MutableSortedBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends MutableBagFactory[CC, mutable.SortedBagConfiguration] {

  def configuration = mutable.SortedBagConfiguration

}