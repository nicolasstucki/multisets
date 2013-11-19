
package scala.collection
package generic

import scala.language.higherKinds


trait GenericBagCompanion[CC[X] <: collection.Bag[X]] {

  type Coll = CC[_]

  type BagBucket[A] <: collection.BagBucket[A]
  type BagBucketFactory[A] <: collection.BagBucketFactory[A, BagBucket[A]]

  def newBuilder[A](implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]): mutable.BagBuilder[A, CC[A]]

  def empty[A](implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]): CC[A]

  def apply[A](elems: A*)(implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]): CC[A] = {
    if (elems.isEmpty) empty[A]
    else {
      val b = newBuilder[A](bucketFactory, equivClass)
      b ++= elems
      b.result()
    }
  }

  def from[A](elemCounts: (A, Int)*)(implicit bucketFactory: BagBucketFactory[A], equivClass: Equiv[A]): CC[A] = {
    if (elemCounts.isEmpty) empty[A]
    else {
      val b = newBuilder[A](bucketFactory, equivClass)
      for ((elem, count) <- elemCounts) {
        b add(elem, count)
      }
      b.result()
    }
  }


}
