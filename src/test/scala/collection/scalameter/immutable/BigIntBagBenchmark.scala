package scala.collection.scalameter.immutable

import scala.language.higherKinds

import scala.collection.immutable


trait BigIntBagBenchmark extends scala.collection.scalameter.BigIntBagBenchmark {
  type Bag[X] <: scala.collection.immutable.Bag[X]
  type BagBucket[X] = scala.collection.immutable.BagBucket[X]
  type BagBucketFactory[X] <: scala.collection.immutable.BagBucketFactory[X]
}


trait BigIntSortedBagBenchmark extends BigIntBagBenchmark {
  type Bag[X] <: scala.collection.immutable.SortedBag[X]
  type BagBucketFactory[X] = scala.collection.immutable.SortedBagBucketFactory[X]

  def bagBucketFactoryOfVectors: BagBucketFactory[BigInt] = immutable.SortedBagBucketFactory.ofVectors

  def bagBucketFactoryOfMultiplicities: BagBucketFactory[BigInt] = immutable.SortedBagBucketFactory.ofMultiplicities
}