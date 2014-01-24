package scala.collection.scalatest.immutable

import scala.collection.scalatest.IntBagBucketTest
import scala.collection.scalatest.StringBagBucketTest
import scala.collection.{BagBucket, immutable}


class IntImmutableMultiplicityBagBucketTest extends IntBagBucketTest {
  def emptyBagBucket: BagBucket[Int] = new immutable.MultiplicityBagBucket(1, 0)
}

class IntImmutableHashBagOfMultiplicitiesBagBucketTest extends IntBagBucketTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.compact[Int]

  def emptyBagBucket = new immutable.BagOfMultiplicitiesBagBucket(1, immutable.HashBag.empty[Int])
}

class IntImmutableTreeBagOfMultiplicitiesBagBucketTest extends IntBagBucketTest {
  implicit def bagConfiguration = immutable.TreeBag.configuration.compact[Int]

  def emptyBagBucket = new immutable.BagOfMultiplicitiesBagBucket(1, immutable.TreeBag.empty[Int])
}

class IntImmutableListBagBucketTest extends IntBagBucketTest {
  def emptyBagBucket: BagBucket[Int] = new immutable.ListBagBucket(1, List.empty[Int])
}


class StringImmutableMultiplicityBagBucketTest extends StringBagBucketTest {
  def emptyBagBucket: BagBucket[String] = new immutable.MultiplicityBagBucket("Cat", 0)
}

class StringImmutableHashBagOfMultiplicitiesBagBucketTest extends StringBagBucketTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.compact[String]

  def emptyBagBucket = new immutable.BagOfMultiplicitiesBagBucket("Cat", immutable.HashBag.empty[String])
}

class StringImmutableTreeBagOfMultiplicitiesBagBucketTest extends StringBagBucketTest {
  implicit def bagConfiguration = immutable.TreeBag.configuration.compact[String]

  def emptyBagBucket = new immutable.BagOfMultiplicitiesBagBucket("Cat", immutable.TreeBag.empty[String])
}

class StringImmutableListBagBucketTest extends StringBagBucketTest {
  def emptyBagBucket: BagBucket[String] = new immutable.ListBagBucket("Cat", List.empty[String])
}

