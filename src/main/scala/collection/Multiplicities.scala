package scala.collection


class Multiplicities[A](bag: Bag[A]) extends Map[A, Int] {

  def get(key: A): Option[Int] = Some(bag.multiplicity(key))

  def iterator: Iterator[(A, Int)] = bag.bucketsIterator flatMap (bucket => bucket.distinctIterator.map(elem => (elem, bucket.multiplicity(elem))))

  def +[B1 >: Int](kv: (A, B1)): Map[A, B1] = Map.empty[A, B1] ++ this + kv

  def -(key: A): Multiplicities[A] = new Multiplicities(bag removedAll key)

}
