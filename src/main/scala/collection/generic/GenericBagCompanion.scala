
package scala.collection
package generic

import scala.language.higherKinds


trait GenericBagCompanion[CC[X] <: collection.Bag[X]] {

  type Coll = CC[_]

  type BB[A] <: collection.BagBucket[A]
  type BBC[A] <: collection.BagBucketConfiguration[A, BagBucket[A]]

  def newBuilder[A](implicit bagBucketConfiguration: BBC[A]): mutable.BagBuilder[A, CC[A]]

  def empty[A](implicit bagBucketConfiguration: BBC[A]): CC[A]

  def apply[A](elems: A*)(implicit bagBucketConfiguration: BBC[A]): CC[A] = {
    if (elems.isEmpty) empty[A]
    else {
      val b = newBuilder[A]
      b ++= elems
      b.result()
    }
  }

  def from[A](elemCounts: (A, Int)*)(implicit bagBucketConfiguration: BBC[A]): CC[A] = {
    if (elemCounts.isEmpty) empty[A]
    else {
      val b = newBuilder[A]
      for ((elem, count) <- elemCounts) {
        b add(elem, count)
      }
      b.result()
    }
  }


}
