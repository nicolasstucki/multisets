package scala.collection.scalatest.mutable

import scala.collection.mutable

class IntMutableHashBagOnMultiplicitiesTest extends MutableIntBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compact[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnKeepAllBucketsTest extends MutableIntBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnBagOfMultiplicitiesWithMod3EquivTest extends MutableIntBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compactWithEquiv(Mod3)

  override def emptyBag = mutable.HashBag.empty[Int]
}

class IntMutableHashBagOnKeepAllBucketsWithMod3EquivTest extends MutableIntBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll(Mod3)

  override def emptyBag = mutable.HashBag.empty[Int]
}


class StringMutableHashBagOnMultiplicitiesTest extends MutableStringBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compact[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableHashBagOnKeepAllBucketsBucketTest extends MutableStringBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableHashBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends MutableStringBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compactWithEquiv(StrSize)

  override def emptyBag = mutable.HashBag.empty[String]
}

class StringMutableHashBagOnKeepAllBucketsWithStrSizeEquivTest extends MutableStringBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll(StrSize)

  override def emptyBag = mutable.HashBag.empty[String]
}