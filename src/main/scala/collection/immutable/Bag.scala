package scala.collection.immutable

import scala.collection.{GenTraversable, mutable, BagLike, immutable}

trait Bag[A]
  extends scala.collection.Bag[A]
  with BagLike[A, Bag[A]] {

  protected def bktFactory: immutable.BagBucketFactory[A]

}


object Bag {

  def empty[A](implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag.empty(bktFactory)
  }

  def apply[A](elem: (A, Int))(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag(elem)(bktFactory)
  }

  def apply[A](elem1: (A, Int), elem2: (A, Int), elems: (A, Int)*)(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag(elem1, elem2, elems: _*)(bktFactory)
  }

  def apply[A](elems: GenTraversable[A])(implicit bktFactory: immutable.BagBucketFactory[A]): immutable.Bag[A] = {
    immutable.DummyMapBag(elems)(bktFactory)
  }

}