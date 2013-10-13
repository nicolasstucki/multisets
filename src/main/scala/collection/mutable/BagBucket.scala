package scala.collection.mutable

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  def +=(elem: A): Unit

  def -=(elem: A): Unit
}





