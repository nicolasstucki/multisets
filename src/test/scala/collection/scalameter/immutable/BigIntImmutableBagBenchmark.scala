package scala.collection.scalameter.immutable

import scala.language.higherKinds

import scala.collection.{mutable, TestBagFactory}
import org.scalameter.Gen
import scala.collection.immutable
import org.scalameter.api._

trait BigIntImmutableBagBenchmark extends PerformanceTest.Quickbenchmark {

  type Bag[X] <: scala.collection.immutable.SortedBag[X]

  def sizes: Gen[Int]

  def newBuilder(implicit m: immutable.SortedBagBucketFactory[BigInt]): mutable.BagBuilder[BigInt, Bag[BigInt]]

  def bagName: String

  def funName: String

  def fun(bag: Bag[BigInt]): Unit

  val compareWithLists = true

  def listFun(list: List[BigInt]): Unit

  def benchBags(bags: Gen[Bag[BigInt]]): Unit = measure method funName in (using(bags) in fun)

  def benchLists(lists: Gen[List[BigInt]]): Unit = {
    measure method funName in (using(lists) in listFun)
  }


  def setBag(implicit m: immutable.SortedBagBucketFactory[BigInt]) = for {
    size <- sizes
  } yield {
    TestBagFactory.setBag(size, newBuilder, n => BigInt(n))
  }

  def stepsBag(implicit m: immutable.SortedBagBucketFactory[BigInt]) = for {
    size <- sizes
  } yield {
    TestBagFactory.stepsBag(size, newBuilder, n => BigInt(n))
  }

  def squareBag(implicit m: immutable.SortedBagBucketFactory[BigInt]) = for {
    size <- sizes
  } yield {
    TestBagFactory.squareBag(size, newBuilder, n => BigInt(n))
  }

  def runBenchmark(): Unit = {

    performance of s"$bagName[BigInt]{ofMultiplicities filled as setBag}" in {
      implicit val m = immutable.SortedBagBucketFactory.ofMultiplicities[BigInt]
      benchBags(setBag)
    }
    performance of s"$bagName[BigInt]{ofVectors filled as setBag}" in {
      implicit val m = immutable.SortedBagBucketFactory.ofVectors[BigInt]
      benchBags(setBag)
    }
    if (compareWithLists)
      performance of "List[BigInt]{from setBag}" in {
        implicit val m = immutable.SortedBagBucketFactory.ofMultiplicities[BigInt]
        benchLists(setBag.map(_.toList))
      }


    performance of s"$bagName[BigInt]{ofMultiplicities filled as stepsBag}" in {
      implicit val m = immutable.SortedBagBucketFactory.ofMultiplicities[BigInt]
      benchBags(stepsBag)
    }
    performance of s"$bagName[BigInt]{ofVectors filled as stepsBag}" in {
      implicit val m = immutable.SortedBagBucketFactory.ofVectors[BigInt]
      benchBags(stepsBag)
    }
    if (compareWithLists)
      performance of "List[BigInt]{from stepsBag}" in {
        implicit val m = immutable.SortedBagBucketFactory.ofMultiplicities[BigInt]
        benchLists(stepsBag.map(_.toList))
      }


    performance of s"$bagName[BigInt]{ofMultiplicities filled as squareBag}" in {
      implicit val m = immutable.SortedBagBucketFactory.ofMultiplicities[BigInt]
      benchBags(squareBag)
    }
    performance of s"$bagName[BigInt]{ofVectors filled as squareBag}" in {
      implicit val m = immutable.SortedBagBucketFactory.ofVectors[BigInt]
      benchBags(squareBag)
    }
    if (compareWithLists)
      performance of "List[BigInt]{from squareBag}" in {
        implicit val m = immutable.SortedBagBucketFactory.ofMultiplicities[BigInt]
        benchLists(squareBag.map(_.toList))
      }
  }
}
