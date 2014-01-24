package scala.collection.scalatest

import org.scalatest._

trait IntBagBehaviours extends BagBehaviours with Matchers {
  this: FlatSpec =>


  def intBagBehaviour(bag: => collection.Bag[Int]) {

    it should "grow by 1 with +(elem) operation" in {
      assertResult(bag.size + 1) {
        val newBag = bag + 1
        newBag.size
      }
      assertResult(bag.size + 1) {
        val newBag = bag + 2
        newBag.size
      }
      assertResult(bag.size + 1) {
        val newBag = bag + 10
        newBag.size
      }
    }

    it should "grow by m with +(elem->m) operation" in {
      assertResult(bag.size + 4) {
        (bag + (1 -> 4)).size
      }
      assertResult(bag.size + 1) {
        (bag + (2 -> 1)).size
      }
      assertResult(bag.size) {
        (bag + (3 -> 0)).size
      }
      assertResult(bag.size) {
        (bag + (3 -> -3)).size
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


    it should "implement [sum]" in {
      assertResult(bag.toList.sum) {
        bag.sum
      }
    }

    it should "implement [product]" in {
      assertResult(bag.toList.product) {
        bag.product
      }
    }


  }


}
