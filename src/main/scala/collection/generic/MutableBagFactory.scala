package scala
package collection
package generic

import scala.language.higherKinds
import scala.collection.mutable

abstract class MutableBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BagBucket[X] = mutable.BagBucket[X]
  type BagBucketFactory[X] = mutable.BagBucketFactory[X]

  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder[A, CC[A]](empty[A])

}