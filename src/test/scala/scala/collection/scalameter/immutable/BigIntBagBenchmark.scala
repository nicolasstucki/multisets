package scala.collection.scalameter.immutable

import scala.language.higherKinds

import scala.collection.immutable


trait BigIntBagBenchmark extends scala.collection.scalameter.BigIntBagBenchmark {
  type Bag[X] <: scala.collection.immutable.Bag[X]
  type BagBucket[X] = scala.collection.immutable.BagBucket[X]
  type BagConfiguration[X] <: scala.collection.immutable.BagConfiguration[X]
}


trait BigIntSortedBagBenchmark extends BigIntBagBenchmark {
  type Bag[X] <: scala.collection.immutable.Bag[X]
  type BagConfiguration[X] = scala.collection.immutable.SortedBagConfiguration[X]

  def equivClass: Ordering[BigInt] = implicitly[Ordering[BigInt]]

  def bagBucketFactoryOfMultiplicities: BagConfiguration[BigInt] = immutable.SortedBagConfiguration.compact

  def bagBucketFactoryOfBagBucketBag: BagConfiguration[BigInt] = immutable.SortedBagConfiguration.compactWithEquiv(equivClass)

  def bagBucketFactoryOfVectors: BagConfiguration[BigInt] = immutable.SortedBagConfiguration.keepAll
}