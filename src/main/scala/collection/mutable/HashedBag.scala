package scala.collection.mutable

import scala.collection.mutable
import scala.collection.generic.MutableHashedBagFactory


trait HashedBag[A]
  extends mutable.Bag[A]
  with collection.HashedBag[A]
  with mutable.HashedBagLike[A, mutable.HashedBag[A]] {


}

object HashedBag extends MutableHashedBagFactory[mutable.HashedBag] {

  def empty[A](implicit bucketFactory: BagBucketFactory[A]): mutable.HashedBag[A] = mutable.HashBag.empty
}
