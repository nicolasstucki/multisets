

import scala.collection.mutable


implicit val ttbConfig = mutable.SortedBagConfiguration.keepAll[Int]
val ttb = mutable.TreeBag.empty[Int]
ttb.add(1, 5)
ttb.setMultiplicity(7, 6)
ttb.add(2,2)
ttb.getBucket(2)
ttb += 1
ttb += 2 -> 4
ttb.getBucket(2)
ttb += 3
ttb.size
ttb(1)
ttb(2)
ttb(7)
ttb

ttb drop 6

ttb drop 7


ttb dropWhile (_ < 7)