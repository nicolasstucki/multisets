package scala.collection.scalatest.immutable

import scala.collection.scalatest._
import scala.collection.immutable
import scala.util.hashing.Hashing

class IntImmutableHashBagOnMultiplicitiesTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.ofMultiplicities[Int]

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnKeepAllBucketsBucketTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll[Int]

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnBagOfMultiplicitiesWithMod3EquivTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.ofBagOfMultiplicities(Mod3)

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnKeepAllBucketsBucketsWithMod3EquivTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll(Mod3)

  override def emptyBag = immutable.HashBag.empty[Int]
}


class StringImmutableHashBagOnMultiplicitiesTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.ofMultiplicities[String]

  override def emptyBag = immutable.HashBag.empty[String]
}


class StringImmutableHashBagOnKeepAllBucketsBucketTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll[String]

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHasBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.ofBagOfMultiplicities(StrSize)

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHashBagOnKeepAllBucketsBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll(StrSize)

  override def emptyBag = immutable.HashBag.empty[String]
}
