package scala
package collection
package generic

import mutable.{Builder, BagBuilder}
import scala.language.higherKinds

abstract class ImmutableBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BagBucket[A] = immutable.BagBucket[A]
  type BagBucketFactory[A] = immutable.BagBucketFactory[A]

  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}