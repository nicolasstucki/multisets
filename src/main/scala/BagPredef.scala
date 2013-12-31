package scala

import scala.collection.mutable
import scala.collection.immutable


/*
   predef intended to be in Predef.scala
 */
trait BagPredef {

  implicit def immutableBagConfigurationHashedOfMultiplicities[A] = immutable.BagConfiguration.Hashed.ofMultiplicities[A]

  implicit def mutableBagConfigurationHashedOfMultiplicities[A] = mutable.BagConfiguration.Hashed.ofMultiplicities[A]

  implicit def immutableBagConfigurationSortedOfMultiplicities[A](implicit ordering: Ordering[A]) = immutable.BagConfiguration.Sorted.ofMultiplicities[A](ordering)

  implicit def mutableBagConfigurationSortedOfMultiplicities[A](implicit ordering: Ordering[A]) = mutable.BagConfiguration.Sorted.ofMultiplicities[A](ordering)

}
