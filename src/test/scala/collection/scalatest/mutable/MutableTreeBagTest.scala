package scala.collection.scalatest.mutable

import scala.collection.scalatest._
import scala.collection.mutable

class IntMutableTreeBagOnMultiplicitiesTest extends IntBagTest {
  implicit lazy val bagBucketConfiguration = mutable.SortedBagConfiguration.compact[Int]

  override def emptyBag = mutable.TreeBag.empty[Int]
}

class IntMutableTreeBagOnKeepAllBucketsBucketTest extends IntBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll[Int]

  override def emptyBag = mutable.TreeBag.empty[Int]
}

class IntMutableTreeBagOnBagOfMultiplicitiesWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.compactWithEquiv(Mod3)

  override def emptyBag = mutable.TreeBag.empty[Int]
}

class IntMutableTreeBagOnKeepAllBucketsBucketsWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll[Int](Mod3)

  override def emptyBag = mutable.TreeBag.empty[Int]
}


class StringMutableTreeBagOnMultiplicitiesTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.compact[String]

  override def emptyBag = mutable.TreeBag.empty[String]
}

class StringMutableTreeBagOnKeepAllBucketsBucketTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll[String]

  override def emptyBag = mutable.TreeBag.empty[String]
}

class StringMutableTreeBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.compactWithEquiv(StrSize)

  override def emptyBag = mutable.TreeBag.empty[String]
}

class StringMutableTreeBagOnKeepAllBucketsBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll(StrSize)

  override def emptyBag = mutable.TreeBag.empty[String]
}

