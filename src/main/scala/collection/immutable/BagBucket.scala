package scala.collection.immutable

import scala.collection.immutable

trait BagBucket[A] extends scala.collection.BagBucket[A] {

  def +(elem: A): immutable.BagBucket[A]

  def -(elem: A): immutable.BagBucket[A]
}





