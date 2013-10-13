package scala.collection.immutable

trait BagBucket[A, Bkt <: BagBucket[A, Bkt]] extends scala.collection.BagBucket[A] {

  def +(elem: A): Bkt

  def -(elem: A): Bkt
}





