package scala.collection.scalatest.immutable

import scala.collection.scalatest._
import scala.collection.immutable

class IntImmutableTreeBagOnMultiplicitiesTest extends IntBagTest {
  implicit lazy val bagBucketConfiguration = immutable.SortedBagConfiguration.compact[Int]

  override def emptyBag = immutable.TreeBag.empty[Int]
}

class IntImmutableTreeBagOnKeepAllBucketsBucketTest extends IntBagTest {
  implicit def bagBucketConfiguration = immutable.TreeBag.configuration.keepAll[Int]

  override def emptyBag = immutable.TreeBag.empty[Int]
}

class IntImmutableTreeBagOnBagOfMultiplicitiesWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = immutable.TreeBag.configuration.compactWithEquiv(Mod3)

  override def emptyBag = immutable.TreeBag.empty[Int]
}

class IntImmutableTreeBagOnKeepAllBucketsBucketsWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = immutable.TreeBag.configuration.keepAll[Int](Mod3)

  override def emptyBag = immutable.TreeBag.empty[Int]
}


class StringImmutableTreeBagOnMultiplicitiesTest extends StringBagTest {
  implicit def bagBucketConfiguration = immutable.TreeBag.configuration.compact[String]

  override def emptyBag = immutable.TreeBag.empty[String]
}

class StringImmutableTreeBagOnKeepAllBucketsBucketTest extends StringBagTest {
  implicit def bagBucketConfiguration = immutable.TreeBag.configuration.keepAll[String]

  override def emptyBag = immutable.TreeBag.empty[String]
}

class StringImmutableTreeBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = immutable.TreeBag.configuration.compactWithEquiv(StrSize)

  override def emptyBag = immutable.TreeBag.empty[String]
}

class StringImmutableTreeBagOnKeepAllBucketsBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = immutable.TreeBag.configuration.keepAll(StrSize)

  override def emptyBag = immutable.TreeBag.empty[String]
}

