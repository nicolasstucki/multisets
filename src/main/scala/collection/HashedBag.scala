package scala.collection


trait HashedBag[A]
  extends collection.Bag[A]
  with collection.HashedBagLike[A, HashedBag[A]] {

}


