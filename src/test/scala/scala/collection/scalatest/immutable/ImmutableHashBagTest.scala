package scala.collection.scalatest.immutable

import scala.collection.immutable
import scala.collection.scalatest._

class IntImmutableHashBagOnMultiplicitiesTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.compact[Int]

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnKeepAllBucketsBucketTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll[Int]

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnBagOfMultiplicitiesWithRem3EquivTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.compactWithEquiv(Rem3)

  override def emptyBag = immutable.HashBag.empty[Int]
}

class IntImmutableHashBagOnKeepAllBucketsBucketsWithRem3EquivTest extends IntBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll(Rem3)

  override def emptyBag = immutable.HashBag.empty[Int]
}


class StringImmutableHashBagOnMultiplicitiesTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.compact[String]

  override def emptyBag = immutable.HashBag.empty[String]
}


class StringImmutableHashBagOnKeepAllBucketsBucketTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll[String]

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHashBagOnBagOfMultiplicitiesWithStrSizeEquivTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.compactWithEquiv(StrSize)

  override def emptyBag = immutable.HashBag.empty[String]
}

class StringImmutableHashBagOnKeepAllBucketsBucketsWithStrSizeEquivTest extends StringBagTest {
  implicit def bagConfiguration = immutable.HashBag.configuration.keepAll(StrSize)

  override def emptyBag = immutable.HashBag.empty[String]
}
