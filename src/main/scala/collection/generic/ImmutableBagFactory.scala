package scala
package collection
package generic

import mutable.{Builder, BagBuilder}
import scala.language.higherKinds

abstract class ImmutableBagFactory[CC[X] <: immutable.Bag[X] with immutable.BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BagBucket[X] = immutable.BagBucket[X]
  type BagBucketFactory[X] = immutable.BagBucketFactory[X]

  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = mutable.BagBuilder(empty)

}