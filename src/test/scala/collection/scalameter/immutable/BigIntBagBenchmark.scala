package scala.collection.scalameter.immutable

import scala.language.higherKinds

import scala.collection.immutable


trait BigIntBagBenchmark extends scala.collection.scalameter.BigIntBagBenchmark {
  type Bag[X] <: scala.collection.immutable.Bag[X]
  type BagBucket[X] = scala.collection.immutable.BagBucket[X]
  type BagBucketConfiguration[X] <: scala.collection.immutable.BagBucketConfiguration[X]
}


trait BigIntSortedBagBenchmark extends BigIntBagBenchmark {
  type Bag[X] <: scala.collection.immutable.Bag[X]
  type BagBucketConfiguration[X] = scala.collection.immutable.SortedBagBucketConfiguration[X]

  def bagBucketFactoryOfMultiplicities: BagBucketConfiguration[BigInt] = immutable.BagBucketConfiguration.Sorted.ofMultiplicities

  def bagBucketFactoryOfBagBucketBag: BagBucketConfiguration[BigInt] = immutable.BagBucketConfiguration.Sorted.ofBagBucketBag

  def bagBucketFactoryOfVectors: BagBucketConfiguration[BigInt] = immutable.BagBucketConfiguration.Sorted.ofVectors
}