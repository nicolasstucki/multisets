package scala.collection.scalameter.mutable

import scala.language.higherKinds

import scala.collection.mutable


trait BigIntBagBenchmark extends scala.collection.scalameter.BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.Bag[X]
  type BagBucket[X] = scala.collection.mutable.BagBucket[X]
  type BagBucketFactory[X] <: scala.collection.mutable.BagBucketFactory[X]
}


trait BigIntHashedBagBenchmark extends BigIntBagBenchmark {
  type Bag[X] <: scala.collection.mutable.HashedBag[X]
  type BagBucketFactory[X] = scala.collection.mutable.HashedBagBucketFactory[X]

  def bagBucketFactoryOfVectors: BagBucketFactory[BigInt] = mutable.HashedBagBucketFactory.ofVectors

  def bagBucketFactoryOfMultiplicities: BagBucketFactory[BigInt] = mutable.HashedBagBucketFactory.ofMultiplicities
}