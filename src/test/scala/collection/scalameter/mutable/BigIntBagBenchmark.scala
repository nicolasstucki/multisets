package scala.collection.scalameter.mutable

import scala.language.higherKinds

import scala.collection.mutable


trait BigIntBagBenchmark extends scala.collection.scalameter.BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.Bag[X]
  type BagBucket[X] = scala.collection.mutable.BagBucket[X]
  type BagConfiguration[X] <: scala.collection.mutable.BagConfiguration[X]
}


trait BigIntHashedBagBenchmark extends BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.Bag[X]
  type BagConfiguration[X] = scala.collection.mutable.HashedBagConfiguration[X]

  def bagBucketFactoryOfMultiplicities: BagConfiguration[BigInt] = mutable.BagConfiguration.Hashed.ofMultiplicities

  def bagBucketFactoryOfBagBucketBag: BagConfiguration[BigInt] = mutable.BagConfiguration.Hashed.ofMultiplicities

  def bagBucketFactoryOfVectors: BagConfiguration[BigInt] = mutable.BagConfiguration.Hashed.ofVectors
}