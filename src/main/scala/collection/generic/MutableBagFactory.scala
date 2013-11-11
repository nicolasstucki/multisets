package scala
package collection
package generic

import scala.language.higherKinds
import scala.collection.mutable

abstract class MutableBagFactory[CC[X] <: mutable.Bag[X] with mutable.BagLike[X, CC[X]]]
  extends BagFactory[CC] {

  type BagBucket[A] = mutable.BagBucket[A]

  type BagBucketFactory[A] = mutable.BagBucketFactory[A]

  def defaultBagBucketFactory[A]: BagBucketFactory[A] = mutable.BagBucketFactory.ofMultiplicities[A]


  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A]): mutable.BagBuilder[A, CC[A]] = new mutable.GrowingBagBuilder[A, CC[A]](empty[A])

}