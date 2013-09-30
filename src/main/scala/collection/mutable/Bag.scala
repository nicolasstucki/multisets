package scala.collection.mutable

import scala.collection.mutable

trait Bag[A] extends scala.collection.Bag[A] {


  def update(elem: A, count: Int): Unit

  def +=(elem: A): Unit = this += (elem -> 1)

  def +=(elemCount: (A, Int)): Unit = elemCount match {
    case (elem, count) => update(elem, (this count elem) + count)
  }

  def -=(elem: A): Unit = this -= (elem -> 1)

  def -=(elemCount: (A, Int)): Unit = elemCount match {
    case (elem, count) => update(elem, Math.max((this count elem) - count, 0))
  }


}


object Bag {

  def apply[T](): mutable.Bag[T] = mutable.MapBag()

  def apply[T](elem: (T, Int)): mutable.Bag[T] = mutable.MapBag(elem)

  def apply[T](elem1: (T, Int), elem2: (T, Int), elems: (T, Int)*): mutable.Bag[T] = mutable.MapBag(elem1, elem2, elems: _*)

  def apply[T](elems: scala.collection.Iterable[T]): mutable.Bag[T] = mutable.MapBag(elems)

}