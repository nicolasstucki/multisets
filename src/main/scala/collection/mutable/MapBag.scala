package scala.collection.mutable

import scala.collection.mutable._


class MapBag[A](map: Map[A, Int]) extends Bag[A] {


  def empty: Bag[A] = new MapBag(Map.empty[A, Int])

  // Added elements
  def +(elem: A): Bag[A] = {
    val newMap = map.clone()
    newMap(elem) = newMap.getOrElse(elem, 0)
    new MapBag(newMap)
  }

  // Removed elements
  def -(elem: A): Bag[A] = {
    val newMap = map.clone()
    if (newMap.getOrElse(elem, 0) > 1) {
      newMap(elem) = newMap.getOrElse(elem, 0) - 1
    } else {
      newMap -= elem
    }
    new MapBag(newMap)
  }

  def iterator2: Iterator[Iterator[A]] = map.keysIterator map elemIterator

  private def elemIterator(elem: A): Iterator[A] = for (_ <- (1 to map.getOrElse(elem, 0)).iterator) yield elem

  def update(elem: A, count: Int): Unit = map.update(elem, Math.max(count, 0))

  override def multiplicity(elem: A): Int = map.getOrElse(elem, 0)

  override def distinctIterator = map.keysIterator

  override def countsIterator = map.iterator

  override def iterator =
    for (elem <- map.keysIterator;
         _ <- 1 to map(elem)) yield {
      elem
    }
}


object MapBag {

  def apply[T](): Bag[T] = new MapBag[T](Map.empty[T, Int])

  def apply[T](elem: (T, Int)): Bag[T] = new MapBag[T](Map(elem))

  def apply[T](elem1: (T, Int), elem2: (T, Int), elems: (T, Int)*): Bag[T] = new MapBag[T](Map() + elem1 + elem2 ++ elems)

  def apply[T](elems: scala.collection.Iterable[T]): Bag[T] = new MapBag[T](Map() ++ (elems map (elem => (elem, elems.count(elem == _)))))
}