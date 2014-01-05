package scala.collection


object BagPredef {

  implicit def immutableHashedBagConfigurationOfMultiplicities[A] = immutable.HashedBagConfiguration.ofMultiplicities[A]

  implicit def mutableHashedBagConfigurationOfMultiplicities[A] = mutable.HashedBagConfiguration.ofMultiplicities[A]

  // TODO: find a way to make this work. Problem: diverging implicit expansion
  // implicit def immutableSortedBagConfigurationOfMultiplicities[A](implicit ordering: Ordering[A]) = immutable.SortedBagConfiguration.ofMultiplicities[A](ordering)
  // implicit def mutableSortedBagConfigurationOfMultiplicities[A](implicit ordering: Ordering[A]) = mutable.SortedBagConfiguration.ofMultiplicities[A](ordering)
}
