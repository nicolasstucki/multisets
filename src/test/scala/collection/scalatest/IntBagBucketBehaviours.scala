package scala.collection.scalatest

import org.scalatest._

trait IntBagBucketBehaviours extends BagBucketBehaviours with Matchers {
  this: FlatSpec =>


  def intBagBucketBehaviour(bagBucket: => collection.BagBucket[Int]) {

    it should "grow by 1 with +(elem) operation" in {
      assertResult(bagBucket.size + 1) {
        val newBagBucket = bagBucket + 1
        newBagBucket.size
      }
      assertResult(bagBucket.size + 1) {
        val newBagBucket = bagBucket + 2
        newBagBucket.size
      }
      assertResult(bagBucket.size + 1) {
        val newBagBucket = bagBucket + 10
        newBagBucket.size
      }
    }

    it should "grow by m with +(elem->m) operation" in {
      assertResult(bagBucket.size + 4) {
        (bagBucket + (1 -> 4)).size
      }
      assertResult(bagBucket.size + 1) {
        (bagBucket + (2 -> 1)).size
      }
      assertResult(bagBucket.size) {
        (bagBucket + (3 -> 0)).size
      }
      assertResult(bagBucket.size) {
        (bagBucket + (3 -> -3)).size
      }
    }


    val distinct = bagBucket.distinct

    it should "implement [distinct]: all multiplicities must be one" in {
      for (elem <- distinct) {
        assertResult(1) {
          distinct.multiplicity(elem)
        }
      }
    }

    it should "implement [distinct]: all distinct element must be present" in {
      assertResult(bagBucket.toSet.toList.sorted) {
        distinct.toList.sorted
      }
    }


    it should "implement [sum]" in {
      assertResult(bagBucket.toList.sum) {
        bagBucket.sum
      }
    }

    it should "implement [product]" in {
      assertResult(bagBucket.toList.product) {
        bagBucket.product
      }
    }


  }


}
