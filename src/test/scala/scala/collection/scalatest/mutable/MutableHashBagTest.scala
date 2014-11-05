package scala.collection.scalatest.mutable

import scala.collection.scalatest._
import scala.collection.mutable
import scala.collection.scalatest.IntBagTest

class IntMutableHashBagOnMultiplicitiesTest extends IntBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compact[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnKeepAllBucketsTest extends IntBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnBagOfMultiplicitiesWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compactWithEquiv(Mod3)

  override def emptyBag = mutable.HashBag.empty[Int]
}

class IntMutableHashBagOnKeepAllBucketsWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll(Mod3)

  override def emptyBag = mutable.HashBag.empty[Int]
}


class StringMutableHashBagOnMultiplicitiesTest extends StringBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compact[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableHashBagOnKeepAllBucketsBucketTest extends StringBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableHashBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.compactWithEquiv(StrSize)

  override def emptyBag = mutable.HashBag.empty[String]
}

class StringMutableHashBagOnKeepAllBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll(StrSize)

  override def emptyBag = mutable.HashBag.empty[String]
}