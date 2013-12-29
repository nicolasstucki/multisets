package scala.collection.scalameter.mutable

import scala.language.higherKinds

import scala.collection.mutable


trait BigIntBagBenchmark extends scala.collection.scalameter.BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.Bag[X]
  type BagBucket[X] = scala.collection.mutable.BagBucket[X]
  type BagBucketConfiguration[X] <: scala.collection.mutable.BagBucketConfiguration[X]
}


trait BigIntHashedBagBenchmark extends BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.Bag[X]
  type BagBucketConfiguration[X] = scala.collection.mutable.HashedBagBucketConfiguration[X]

  def bagBucketFactoryOfMultiplicities: BagBucketConfiguration[BigInt] = mutable.BagBucketConfiguration.Hashed.ofMultiplicities

  def bagBucketFactoryOfBagBucketBag: BagBucketConfiguration[BigInt] = mutable.BagBucketConfiguration.Hashed.ofMultiplicities

  def bagBucketFactoryOfVectors: BagBucketConfiguration[BigInt] = mutable.BagBucketConfiguration.Hashed.ofVectors
}