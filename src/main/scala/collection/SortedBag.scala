package scala.collection

import scala.collection.generic.ImmutableSortedBagFactory


trait SortedBag[A]
  extends collection.Bag[A]
  with collection.SortedBagLike[A, SortedBag[A]] {

}


