package scala.collection.mutable

import scala.collection.mutable


class MapBag[A](map: scala.collection.mutable.Map[A, Int]) extends mutable.Bag[A] {

  override def iterator2: Iterator[Iterator[A]] = map.keysIterator map elemIterator

  private def elemIterator(elem: A): Iterator[A] = for (_ <- (1 to map.getOrElse(elem, 0)).iterator) yield elem

  def update(elem: A, count: Int): Unit = map.update(elem, Math.max(count, 0))
}


object MapBag {

  def apply[T](): mutable.Bag[T] = new mutable.MapBag[T](mutable.Map.empty[T, Int])

  def apply[T](elem: (T, Int)): mutable.Bag[T] = new mutable.MapBag[T](mutable.Map(elem))

  def apply[T](elem1: (T, Int), elem2: (T, Int), elems: (T, Int)*): mutable.Bag[T] = new mutable.MapBag[T](mutable.Map() + elem1 + elem2 ++ elems)

  def apply[T](elems: scala.collection.Iterable[T]): mutable.Bag[T] = new mutable.MapBag[T](mutable.Map() ++ (elems map (elem => (elem, elems.count(elem == _)))))
}