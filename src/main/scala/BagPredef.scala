package scala

import scala.collection.mutable
import scala.collection.immutable


/*
   predef intended to be in scala.Predef
 */
trait BagPredef {

  implicit def immutableBagBucketConfigurationHashedOfMultiplicities[A] = immutable.BagBucketConfiguration.Hashed.ofMultiplicities[A]

  implicit def mutableBagBucketConfigurationHashedOfMultiplicities[A] = mutable.BagBucketConfiguration.Hashed.ofMultiplicities[A]

  implicit def immutableBagBucketConfigurationSortedOfMultiplicities[A](implicit ordering: Ordering[A]) = immutable.BagBucketConfiguration.Sorted.ofMultiplicities[A](ordering)

  implicit def mutableBagBucketConfigurationSortedOfMultiplicities[A](implicit ordering: Ordering[A]) = mutable.BagBucketConfiguration.Sorted.ofMultiplicities[A](ordering)

}
