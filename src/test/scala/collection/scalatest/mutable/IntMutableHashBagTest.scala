package scala.collection.scalatest.mutable

import scala.collection.scalatest._
import scala.collection.mutable
import scala.collection.scalatest.IntBagTest

class IntMutableHashBagOnMultiplicitiesTest extends IntBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.ofMultiplicities[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableHashBagOnKeepAllBucketsTest extends IntBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll[Int]

  override def emptyBag: mutable.Bag[Int] = mutable.HashBag.empty[Int]

}

class IntMutableTreeBagOnBagOfMultiplicitiesWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.ofBagOfMultiplicities(Mod3)

  override def emptyBag = mutable.HashBag.empty[Int]
}

class IntMutableTreeBagOnVectorBucketsWithMod3EquivTest extends IntBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll(Mod3)

  override def emptyBag = mutable.HashBag.empty[Int]
}


class StringMutableHashBagOnMultiplicitiesTest extends StringBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.ofMultiplicities[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableHashBagOnKeepAllBucketsBucketTest extends StringBagTest {

  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll[String]

  override def emptyBag: mutable.Bag[String] = mutable.HashBag.empty[String]

}

class StringMutableTreeBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.ofBagOfMultiplicities(StrSize)

  override def emptyBag = mutable.HashBag.empty[String]
}

class StringMutableTreeBagOnKeepAllBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit def bagBucketConfiguration = mutable.HashBag.configuration.keepAll(StrSize)

  override def emptyBag = mutable.HashBag.empty[String]
}