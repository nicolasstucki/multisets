package scala.collection.scalatest

import org.scalatest._

trait StringBagBucketBehaviours extends BagBucketBehaviours with Matchers {
  this: FlatSpec =>


  def stringBagBucketBehaviour(bag: => collection.BagBucket[String]) {

    it should "grow by 1 with +(elem) operation" in {
      assertResult(bag.size + 1) {
        val newBagBucket = bag + "cat"
        newBagBucket.size
      }
      assertResult(bag.size + 1) {
        val newBagBucket = bag + "dog"
        newBagBucket.size
      }
      assertResult(bag.size + 1) {
        val newBagBucket = bag + "mouse"
        newBagBucket.size
      }
    }

    it should "grow by m with +(elem->m) operation" in {
      assertResult(bag.size + 4) {
        (bag + ("cat" -> 4)).size
      }
      assertResult(bag.size + 1) {
        (bag + ("dog" -> 1)).size
      }
      assertResult(bag.size) {
        (bag + ("fish" -> 0)).size
      }
      assertResult(bag.size) {
        (bag + ("fish" -> -3)).size
      }
    }


    val distinct = bag.distinct

    it should "implement [distinct]: all multiplicities must be one" in {
      for (elem <- distinct) {
        assertResult(1) {
          distinct.multiplicity(elem)
        }
      }
    }

    it should "implement [distinct]: all distinct element must be present" in {
      assertResult(bag.toSet.toList.sorted) {
        distinct.toList.sorted
      }
    }

    it should "have the same size when mapped" in {
      assertResult(bag.size) {
        (bag map (s => s)).size
      }
      assertResult(bag.size) {
        (bag map (s => s.toLowerCase)).size
      }
      assertResult(bag.size) {
        (bag map (s => "cat")).size
      }
    }

    if (bag.nonEmpty) {
      it should "implement reduce coherently" in {
        assertResult(bag.toList.reduce(_ + _)) {
          bag.reduce(_ + _)
        }
      }
    }
  }


}
