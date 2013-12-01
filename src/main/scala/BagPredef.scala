package scala

import scala.collection.mutable
import scala.collection.immutable


/*
   predef intended to be in scala.Predef
 */
trait BagPredef {

  implicit val mutableBagBucketFactoryHashedOfMultiplicitiesInt = mutable.BagBucketFactory.Hashed.ofMultiplicities[Int]
  implicit val mutableBagBucketFactoryHashedOfMultiplicitiesBigInt = mutable.BagBucketFactory.Hashed.ofMultiplicities[BigInt]
  implicit val mutableBagBucketFactoryHashedOfMultiplicitiesString = mutable.BagBucketFactory.Hashed.ofMultiplicities[String]

  implicit val mutableBagBucketFactorySortedOfMultiplicitiesInt = mutable.BagBucketFactory.Sorted.ofMultiplicities[Int]
  implicit val mutableBagBucketFactorySortedOfMultiplicitiesBigInt = mutable.BagBucketFactory.Sorted.ofMultiplicities[BigInt]
  implicit val mutableBagBucketFactorySortedOfMultiplicitiesString = mutable.BagBucketFactory.Sorted.ofMultiplicities[String]

  implicit val immutableBagBucketFactoryHashedOfMultiplicitiesInt = immutable.BagBucketFactory.Hashed.ofMultiplicities[Int]
  implicit val immutableBagBucketFactoryHashedOfMultiplicitiesBigInt = immutable.BagBucketFactory.Hashed.ofMultiplicities[BigInt]
  implicit val immutableBagBucketFactoryHashedOfMultiplicitiesString = immutable.BagBucketFactory.Hashed.ofMultiplicities[String]

  implicit val immutableBagBucketFactorySortedOfMultiplicitiesInt = immutable.BagBucketFactory.Sorted.ofMultiplicities[Int]
  implicit val immutableBagBucketFactorySortedOfMultiplicitiesBigInt = immutable.BagBucketFactory.Sorted.ofMultiplicities[BigInt]
  implicit val immutableBagBucketFactorySortedOfMultiplicitiesString = immutable.BagBucketFactory.Sorted.ofMultiplicities[String]
}
