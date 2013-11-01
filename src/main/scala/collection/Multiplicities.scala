package scala.collection


class Multiplicities[A](bag: Bag[A]) extends Map[A, Int] {

  def get(key: A): Option[Int] = Some(bag(key).multiplicity)

  def iterator: Iterator[(A, Int)] = bag.bucketsIterator map (g => (g.sentinel, g.multiplicity))

  def +[B1 >: Int](kv: (A, B1)): Map[A, B1] = Map.empty[A, B1] ++ this + kv

  def -(key: A): Multiplicities[A] = new Multiplicities(bag -* key)

}
