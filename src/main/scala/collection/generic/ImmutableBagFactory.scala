package scala
package collection
package generic

import mutable.{Builder, BagBuilder}
import scala.language.higherKinds

abstract class ImmutableBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BB[X] = immutable.BagBucket[X]
  type BBC[X] = immutable.BagBucketConfiguration[X]

  def newBuilder[A](implicit bagBucketConfiguration: BBC[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}