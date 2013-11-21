package scala.collection.scalatest

import org.scalatest._

trait IntBagBehaviors extends BagBehaviors with Matchers {
  this: FlatSpec =>


  def intBagBehavior(bag: => collection.Bag[Int]) {

    it should "grow by 1 with +(elem) operation" in {
      assertResult(bag.size + 1) {
        (bag + 1).size
      }
      assertResult(bag.size + 1) {
        (bag + 2).size
      }
      assertResult(bag.size + 1) {
        (bag + 10).size
      }
    }

    it should "grow by m with +(elem->m) operation" in {
      assertResult(bag.size + 4) {
        (bag added (1 -> 4)).size
      }
      assertResult(bag.size + 1) {
        (bag added (2 -> 1)).size
      }
      assertResult(bag.size) {
        (bag added (3 -> 0)).size
      }
      assertResult(bag.size) {
        (bag added (3 -> -3)).size
      }
    }


    val distinct = bag.distinct

    it should "implement [distinct]: all multiplicities must be one" in {
      assert(distinct.forall(distinct.multiplicity(_) == 1))
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
