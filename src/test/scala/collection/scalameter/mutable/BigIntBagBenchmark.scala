package scala.collection.scalameter.mutable

import scala.language.higherKinds

import scala.collection.mutable
import scala.util.hashing.Hashing


trait BigIntBagBenchmark extends scala.collection.scalameter.BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.Bag[X]
  type BagBucket[X] = scala.collection.mutable.BagBucket[X]
  type BagConfiguration[X] <: scala.collection.mutable.BagConfiguration[X]
}


trait BigIntHashedBagBenchmark extends BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.Bag[X]
  type BagConfiguration[X] = scala.collection.mutable.HashedBagConfiguration[X]

  def equivClass: Equiv[BigInt] with Hashing[BigInt] = collection.HashedBagConfiguration.defaultHashedEquiv[BigInt]

  def bagBucketFactoryOfMultiplicities: BagConfiguration[BigInt] = mutable.HashedBagConfiguration.compact

  def bagBucketFactoryOfBagBucketBag: BagConfiguration[BigInt] = mutable.HashedBagConfiguration.compactWithEquiv(equivClass)

  def bagBucketFactoryOfVectors: BagConfiguration[BigInt] = mutable.HashedBagConfiguration.keepAll
}