package scala.collection.immutable

import scala.collection.immutable
import scala.collection.generic.ImmutableHashedBagFactory


trait HashedBag[A]
  extends immutable.Bag[A]
  with collection.HashedBag[A]
  with immutable.HashedBagLike[A, immutable.HashedBag[A]] {


}

object HashedBag extends ImmutableHashedBagFactory[HashedBag] {

  def empty[A](implicit bucketFactory: BagBucketFactory[A]): immutable.HashedBag[A] = ???
}
