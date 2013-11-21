package scala.collection.mutable

import scala.collection.mutable
import scala.collection.generic.MutableSortedBagFactory


trait SortedBag[A]
  extends mutable.Bag[A]
  with collection.SortedBag[A]
  with mutable.SortedBagLike[A, mutable.SortedBag[A]] {


}

object SortedBag extends MutableSortedBagFactory[mutable.SortedBag] {

  def empty[A](implicit bucketFactory: BagBucketFactory[A]): mutable.SortedBag[A] = ???
}
