package scala

import scala.collection.mutable
import scala.collection.immutable


/*
   predef intended to be in scala.Predef
 */
trait BagPredef {

  implicit val mutableBagBucketConfigurationHashedOfMultiplicitiesInt = mutable.BagBucketConfiguration.Hashed.ofMultiplicities[Int]
  implicit val mutableBagBucketConfigurationHashedOfMultiplicitiesBigInt = mutable.BagBucketConfiguration.Hashed.ofMultiplicities[BigInt]
  implicit val mutableBagBucketConfigurationHashedOfMultiplicitiesString = mutable.BagBucketConfiguration.Hashed.ofMultiplicities[String]

  implicit val mutableBagBucketConfigurationSortedOfMultiplicitiesInt = mutable.BagBucketConfiguration.Sorted.ofMultiplicities[Int]
  implicit val mutableBagBucketConfigurationSortedOfMultiplicitiesBigInt = mutable.BagBucketConfiguration.Sorted.ofMultiplicities[BigInt]
  implicit val mutableBagBucketConfigurationSortedOfMultiplicitiesString = mutable.BagBucketConfiguration.Sorted.ofMultiplicities[String]

  implicit val immutableBagBucketConfigurationHashedOfMultiplicitiesInt = immutable.BagBucketConfiguration.Hashed.ofMultiplicities[Int]
  implicit val immutableBagBucketConfigurationHashedOfMultiplicitiesBigInt = immutable.BagBucketConfiguration.Hashed.ofMultiplicities[BigInt]
  implicit val immutableBagBucketConfigurationHashedOfMultiplicitiesString = immutable.BagBucketConfiguration.Hashed.ofMultiplicities[String]

  implicit val immutableBagBucketConfigurationSortedOfMultiplicitiesInt = immutable.BagBucketConfiguration.Sorted.ofMultiplicities[Int]
  implicit val immutableBagBucketConfigurationSortedOfMultiplicitiesBigInt = immutable.BagBucketConfiguration.Sorted.ofMultiplicities[BigInt]
  implicit val immutableBagBucketConfigurationSortedOfMultiplicitiesString = immutable.BagBucketConfiguration.Sorted.ofMultiplicities[String]
}
