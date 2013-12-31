package scala
package collection
package generic

import mutable.Builder
import scala.language.higherKinds


abstract class GenBagFactory[CC[X] <: Bag[X] with GenBagLike[X, CC[X]], BB[X] <: collection.BagBucket[X], BC[X] <: collection.BagConfiguration[X, BB[X]]]
  extends GenericBagCompanion[CC, BB, BC] {

  def newBuilder[A](implicit bagConfiguration: BC[A]): mutable.BagBuilder[A, CC[A]]

  def bagCanBuildFrom[A](implicit bagConfiguration: BC[A]) = new CanBuildFrom[CC[_], A, CC[A]] {
    def apply(from: CC[_]) = newBuilder[A]

    def apply() = newBuilder[A]
  }
}