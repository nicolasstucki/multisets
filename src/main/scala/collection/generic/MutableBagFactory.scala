package scala
package collection
package generic

import scala.language.higherKinds
import scala.collection.mutable

abstract class MutableBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BB[X] = mutable.BagBucket[X]
  type BBC[X] = mutable.BagBucketConfiguration[X]

  def newBuilder[A](implicit bucketFactory: BBC[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder[A, CC[A]](empty[A])

}