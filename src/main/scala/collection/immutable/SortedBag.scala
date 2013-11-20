package scala.collection.immutable

import scala.collection.immutable
import scala.collection.generic.ImmutableSortedBagFactory


trait SortedBag[A]
  extends immutable.Bag[A]
  with collection.SortedBag[A]
  with immutable.SortedBagLike[A, immutable.SortedBag[A]] {


}

object SortedBag extends ImmutableSortedBagFactory[SortedBag] {

  def empty[A](implicit bucketFactory: BagBucketFactory[A]): SortedBag[A] = TreeBag.empty
}
