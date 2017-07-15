package scala.collection.scalatest.mutable

import scala.collection.mutable

class IntMutableTreeBagOnMultiplicitiesTest extends MutableIntBagTest {
  implicit lazy val bagBucketConfiguration = mutable.SortedBagConfiguration.compact[Int]

  override def emptyBag = mutable.TreeBag.empty[Int]
}

class IntMutableTreeBagOnKeepAllBucketsBucketTest extends MutableIntBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll[Int]

  override def emptyBag = mutable.TreeBag.empty[Int]
}

class IntMutableTreeBagOnBagOfMultiplicitiesWithRem3EquivTest extends MutableIntBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.compactWithEquiv(Rem3)

  override def emptyBag = mutable.TreeBag.empty[Int]
}

class IntMutableTreeBagOnKeepAllBucketsBucketsWithRem3EquivTest extends MutableIntBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll[Int](Rem3)

  override def emptyBag = mutable.TreeBag.empty[Int]
}


class StringMutableTreeBagOnMultiplicitiesTest extends MutableStringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.compact[String]

  override def emptyBag = mutable.TreeBag.empty[String]
}

class StringMutableTreeBagOnKeepAllBucketsBucketTest extends MutableStringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll[String]

  override def emptyBag = mutable.TreeBag.empty[String]
}

class StringMutableTreeBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends MutableStringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.compactWithEquiv(StrSize)

  override def emptyBag = mutable.TreeBag.empty[String]
}

class StringMutableTreeBagOnKeepAllBucketsBucketsWithStrSizeEquivTest extends MutableStringBagTest {
  implicit def bagBucketConfiguration = mutable.TreeBag.configuration.keepAll(StrSize)

  override def emptyBag = mutable.TreeBag.empty[String]
}

