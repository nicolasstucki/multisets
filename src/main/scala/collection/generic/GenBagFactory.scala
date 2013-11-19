package scala
package collection
package generic

import mutable.Builder
import scala.language.higherKinds


abstract class GenBagFactory[CC[X] <: Bag[X] with GenBagLike[X, CC[X]]]
  extends GenericBagCompanion[CC] {

  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]): mutable.BagBuilder[A, CC[A]]

  def bagCanBuildFrom[A](implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]) = new CanBuildFrom[CC[_], A, CC[A]] {
    def apply(from: CC[_]) = newBuilder[A](bucketFactory, equivClass)

    def apply() = newBuilder[A](bucketFactory, equivClass)
  }
}