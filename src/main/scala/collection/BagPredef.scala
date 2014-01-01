package scala.collection


package object BagPredef {

  implicit def immutableHashedBagConfigurationOfMultiplicities[A] = immutable.BagConfiguration.Hashed.ofMultiplicities[A]

  implicit def mutableHashedBagConfigurationOfMultiplicities[A] = mutable.BagConfiguration.Hashed.ofMultiplicities[A]

  implicit def immutableSortedBagConfigurationOfMultiplicities[A](implicit ordering: Ordering[A]) = immutable.BagConfiguration.Sorted.ofMultiplicities[A](ordering)

  implicit def mutableSortedBagConfigurationOfMultiplicities[A](implicit ordering: Ordering[A]) = mutable.BagConfiguration.Sorted.ofMultiplicities[A](ordering)

}
