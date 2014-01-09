
package scala.collection
package generic

import scala.language.higherKinds


trait GenericBagCompanion[CC[X] <: collection.Bag[X], BB[X] <: collection.BagBucket[X], BC[X] <: collection.BagConfiguration[X, BB[X]]] {

  type Coll = CC[_]


  def newBuilder[A](implicit bagConfiguration: BC[A]): mutable.BagBuilder[A, CC[A]]

  def empty[A](implicit bagConfiguration: BC[A]): CC[A]

  def apply[A](implicit bagConfiguration: BC[A]): CC[A] = empty[A]

  def apply[A](elems: A*)(implicit bagConfiguration: BC[A]): CC[A] = {
    if (elems.isEmpty) empty[A]
    else (newBuilder[A] ++= elems).result()
  }

  def from[A](elemCounts: (A, Int)*)(implicit bagConfiguration: BC[A]): CC[A] = {
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
