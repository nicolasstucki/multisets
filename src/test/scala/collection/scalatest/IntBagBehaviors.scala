package scala.collection.scalatest

import org.scalatest._

trait IntBagBehaviors extends BagBehaviors with Matchers {
  this: FlatSpec =>


  def intBagBehavior(bag: => collection.Bag[Int]) {

    it should "grow by 1 with +(elem) operation" in {
      assert((bag + 1).size == bag.size + 1)
      assert((bag + 2).size == bag.size + 1)
      assert((bag + 10).size == bag.size + 1)
    }

    it should "grow by m with +(elem->m) operation" in {
      assert((bag + (1 -> 4)).size == bag.size + 4)
      assert((bag + (2 -> 1)).size == bag.size + 1)
      assert((bag + (3 -> 0)).size == bag.size)
      assert((bag + (3 -> -3)).size == bag.size)
    }


    val distinct = bag.distinct

    it should "implement [distinct]: all multiplicities must be one" in {
      assert(distinct.bucketsIterator.forall(_.multiplicity == 1))
    }

    it should "implement [distinct]: all distinct element must be present" in {
      assert(distinct.toList.sorted === bag.toSet.toList.sorted)
    }


    it should "implement [sum]" in {
      assert(bag.sum === bag.toList.sum)
    }

    it should "implement [product]" in {
      assert(bag.product === bag.toList.product)
    }


  }



}
