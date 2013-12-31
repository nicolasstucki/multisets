package scala.collection.scalameter

import scala.language.higherKinds

import scala.collection.TestBagFactory
import org.scalameter.Gen
import org.scalameter.api._

trait BigIntBagBenchmark extends PerformanceTest.Quickbenchmark {

  type Bag[X] <: scala.collection.Bag[X]
  type BagBucket[X] <: scala.collection.BagBucket[X]
  type BagConfiguration[X] <: scala.collection.BagConfiguration[X, BagBucket[X]]


  def sizes: Gen[Int]

  def newBuilder(implicit m: BagConfiguration[BigInt]): scala.collection.mutable.BagBuilder[BigInt, Bag[BigInt]]

  def bagName: String

  def funName: String

  def fun(bag: Bag[BigInt]): Unit

  val compareWithLists = true

  def listFun(list: List[BigInt]): Unit

  def benchBags(bags: Gen[Bag[BigInt]]): Unit = measure method funName in (using(bags) in fun)

  def benchLists(lists: Gen[List[BigInt]]): Unit = {
    measure method funName in (using(lists) in listFun)
  }


  def setBag(implicit m: BagConfiguration[BigInt]) = for {
    size <- sizes
  } yield {
    TestBagFactory.setBag(size, newBuilder, n => BigInt(n))
  }

  def stepsBag(implicit m: BagConfiguration[BigInt]) = for {
    size <- sizes
  } yield {
    TestBagFactory.stepsBag(size, newBuilder, n => BigInt(n))
  }

  def squareBag(implicit m: BagConfiguration[BigInt]) = for {
    size <- sizes
  } yield {
    TestBagFactory.squareBag(size, newBuilder, n => BigInt(n))
  }

  def bagBucketFactoryOfMultiplicities: BagConfiguration[BigInt]

  def bagBucketFactoryOfBagBucketBag: BagConfiguration[BigInt]

  def bagBucketFactoryOfVectors: BagConfiguration[BigInt]

  def runBenchmark(): Unit = {

    performance of s"$bagName[BigInt]{ofMultiplicities filled as setBag}" in {
      implicit val m = bagBucketFactoryOfMultiplicities
      benchBags(setBag)
    }

    performance of s"$bagName[BigInt]{ofBagBucketBag filled as setBag}" in {
      implicit val m = bagBucketFactoryOfMultiplicities
      benchBags(setBag)
    }

    performance of s"$bagName[BigInt]{ofVectors filled as setBag}" in {
      implicit val m = bagBucketFactoryOfVectors
      benchBags(setBag)
    }

    if (compareWithLists)
      performance of "List[BigInt]{from setBag}" in {
        implicit val m = bagBucketFactoryOfMultiplicities
        benchLists(setBag.map(_.toList))
      }


    performance of s"$bagName[BigInt]{ofMultiplicities filled as stepsBag}" in {
      implicit val m = bagBucketFactoryOfMultiplicities
      benchBags(stepsBag)
    }

    performance of s"$bagName[BigInt]{ofBagBucketBag filled as stepsBag}" in {
      implicit val m = bagBucketFactoryOfBagBucketBag
      benchBags(stepsBag)
    }

    performance of s"$bagName[BigInt]{ofVectors filled as stepsBag}" in {
      implicit val m = bagBucketFactoryOfVectors
      benchBags(stepsBag)
    }

    if (compareWithLists)
      performance of "List[BigInt]{from stepsBag}" in {
        implicit val m = bagBucketFactoryOfMultiplicities
        benchLists(stepsBag.map(_.toList))
      }


    performance of s"$bagName[BigInt]{ofMultiplicities filled as squareBag}" in {
      implicit val m = bagBucketFactoryOfMultiplicities
      benchBags(squareBag)
    }

    performance of s"$bagName[BigInt]{ofBagBucketBag filled as squareBag}" in {
      implicit val m = bagBucketFactoryOfBagBucketBag
      benchBags(squareBag)
    }

    performance of s"$bagName[BigInt]{ofVectors filled as squareBag}" in {
      implicit val m = bagBucketFactoryOfVectors
      benchBags(squareBag)
    }

    if (compareWithLists)
      performance of "List[BigInt]{from squareBag}" in {
        implicit val m = bagBucketFactoryOfMultiplicities
        benchLists(squareBag.map(_.toList))
      }
  }
}
