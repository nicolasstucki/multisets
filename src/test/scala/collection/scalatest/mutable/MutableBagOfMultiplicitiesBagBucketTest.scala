package scala.collection.scalatest.mutable

import scala.collection.scalatest.IntBagBucketTest
import scala.collection.scalatest.StringBagBucketTest
import scala.collection.{BagBucket, mutable}


class IntMutableMultiplicityBagBucketTest extends IntBagBucketTest {
  def emptyBagBucket: BagBucket[Int] = new mutable.MultiplicityBagBucket(1, 0)
}

class IntMutableHashBagOfMultiplicitiesBagBucketTest extends IntBagBucketTest {
  implicit def bagConfiguration = mutable.HashBag.configuration.compact[Int]

  def emptyBagBucket = new mutable.BagOfMultiplicitiesBagBucket(1, mutable.HashBag.empty[Int])
}

class IntMutableTreeBagOfMultiplicitiesBagBucketTest extends IntBagBucketTest {
  implicit def bagConfiguration = mutable.TreeBag.configuration.compact[Int]

  def emptyBagBucket = new mutable.BagOfMultiplicitiesBagBucket(1, mutable.TreeBag.empty[Int])
}

class IntMutableListBagBucketTest extends IntBagBucketTest {
  def emptyBagBucket: BagBucket[Int] = new mutable.ListBagBucket(1, List.empty[Int])
}


class StringMutableMultiplicityBagBucketTest extends StringBagBucketTest {
  def emptyBagBucket: BagBucket[String] = new mutable.MultiplicityBagBucket("Cat", 0)
}

class StringMutableHashBagOfMultiplicitiesBagBucketTest extends StringBagBucketTest {
  implicit def bagConfiguration = mutable.HashBag.configuration.compact[String]

  def emptyBagBucket = new mutable.BagOfMultiplicitiesBagBucket("Cat", mutable.HashBag.empty[String])
}

class StringMutableTreeBagOfMultiplicitiesBagBucketTest extends StringBagBucketTest {
  implicit def bagConfiguration = mutable.TreeBag.configuration.compact[String]

  def emptyBagBucket = new mutable.BagOfMultiplicitiesBagBucket("Cat", mutable.TreeBag.empty[String])
}

class StringMutableListBagBucketTest extends StringBagBucketTest {
  def emptyBagBucket: BagBucket[String] = new mutable.ListBagBucket("Cat", List.empty[String])
}

