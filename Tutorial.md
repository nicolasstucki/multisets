Tutorial
========

Trait Bag
---------
The `Bag` trait represents multisets. A bag is a kind of iterable that groups elements together. The goal is to take advantage of these groupings to compact the space required to keep elements in the data structure and/or make some methods execute faster.

Groupings are represented by the `BagBucket` trait. A bucket is an iterable that contains only equivalent elements. It additionally exposes the multiplicities of elements inside it and a sentinel (or representative element) that is equivalent to all elements of that bucket. All implementations of `Bag` are collections of bucket where each bucket represents a different equivalency of elements.

The operations on bags fall into the following categories:
* **Lookup**: `multiplicity`, `multiplicities`. The `multiplicity` method returns the multiplicity of a given element. The `multiplicities` method returns a `Map` from elements to their multiplicity (0 if not contained).
* **Tests**: `contains`, `subsetOf`. The `contains` method asks if some element is contained in the multiset. The `subsetOf` is the musliset subset operation in relation to the other bag (`A` subsetOf `B` is equivalent to `A.forall(e=>A.multiplicity(e)<=B.multiplicity(e))`).
* **Equivalences**: `apply`, `mostCommon`, `leastCommon`. The `apply` receives an element and return a bag with all equivalent elements. The `mostCommon`/`leastCommon` method returns a bag with all the elements for which the number of equivalent elements is maximized/minimized. The `mostCommon`/`leastCommon` method may return a bag with several groupings.
* **Additions**: `+`, `added`. The `+` method adds the element to the bag. The `added` method adds some number of times the same element.
* **Removals**: `-`, `removed`, `removedAll`. The - method removes one time element from the bag. The removed method removes some number of times the same element. The `removedAll` removes all instances of some element.
* **Updates**: `withMultiplicity`. The method `withMultiplicity` returns a bag with the multiplicity of some element set to some specified value.
* **Multiset operations**: `union`, `diff`, `intersect`, `distinct`, `maxUnion` (generalized set union). The `union`/`diff` method yields the same result as the `++`/`--` method. The intersect yields the multiset intersection. This implies that the multiplicity of every element in the intersection is equal to the minimum multiplicity of that element in the original bags. The `distinct` method yields a `Bag` with all the distinct elements exactly once. The `maxUnion` method yields a bag resulting from the generalized set union. This union is similar to the multiset intersection but with maximum multiplicities.
* **Iterators**: `iterator`, `distinctIterator`, `bucketsIterator`. The iterator is the normal iterator from Iterable. The `distinctIterator` returns an iterator of distinct elements, where every element is not equal to the others. The bucketsIterator returns an iterator over buckets, where elements in different bucket are not equivalent.

When a bag is mutable, it also offers side-effecting update methods:
* **Additions**: `+=`, `add`. The `+=` methods adds an element to the bag. The add method adds a number of times some element.
* **Removals**: `-=`, `remove`, `removeAll`. The `-=` method removes one instance of an element. The remove method removes a number of times some element. The `removeAll` removes all instances of some element.
* **Updates**: `setMultiplicity`. The `setMultiplicity` method sets the multiplicity of some element  to match some specified value.

How to use Bag (HashBag/TreeBag)
--------------------------------
This section will show how to use `Bag`s with the default equivalence (i.e. natural equality `==` ). The default `Bag` is the `immutable.HashBag`. This bag uses hashing to access to the `BagBucket` buckets in constant time. There is also an implementation based on red-black trees called `TreeBag`. This implementation has the added property of keeping buckets in order and has logarithmic bucket access time. For now the order of elements will be the their natural order.

```scala
// immutable Bag with a hash table containing the bag buckets
import scala.collection.Bag
import scala.collection.immutable.{HashBag=>Bag}
// or immutable Bag with a red-black tree containing the buckets
import scala.collection.immutable.{TreeBag=>Bag}
// or mutable Bag with a hash table containing the bag buckets
import scala.collection.mutable.{HashBag=>Bag}
// or mutable Bag with a red-black tree containing the buckets
import scala.collection.mutable.{TreeBag=>Bag}
```

It is also necessary to have an implicit `BagConfiguration`. The configurations can be accessed directly with the companion object of the `Bag`. It is possible to choose keeping a count of the elements with `compact[T]` or keeping references to all the elements in the collection using `keepAll[T]`. Note that the configuration is spawned from the implementation itself and therefore is specific to it. This implies that the configuration of one implementation may not be compatible with another implementation.

```scala
// Configuration of that keeps one reference to the element and it’s multiplicity
implicit config = Bag.configuration.compact[String]
// or a configuration of that keeps all references to the elements
implicit config = Bag.configuration.keepAll[String]
```

Now it is possible to create instances of `Bag`. For this example the bag will contain one cat, three dogs and two mice. The following are equivalent statements:

```scala
// use the constructor form multiplicities of elements
val bag = Bag.from("cat"->1, "dog"->3, "mouse"->2)
// or enumerate all elements
val bag = Bag("cat", "dog", "dog", "dog", "mouse")
// or adding elements one by one
val bag = Bag.empty[String] + "cat" + "dog" + "dog" + "dog" + "mouse"
// or adding several times the same element
val bag = Bag.empty[String] added ("cat"->1) added ("dog"->3) added ("mouse"->1)
// or by setting the multiplicity of an element
val bag = Bag.empty[String] withMultiplicity ("cat", 1) withMultiplicity ("dog", 3) withMultiplicity ("mouse",1)

bag.toString >>> Bag(dog, dog, dog; cat; mouse)
```
Note that a semi-colon separates the buckets and commas separate the elements inside it. This semi-colon does not work in constructors.

Here are some simple examples of how the Bag operations are used and their results:
```scala
// The apply method returns a bag with all equivalent elements,
// this is more useful when using a custom equivalence (refer to ‘How to use Bag with custom equivalences’)
bag("dog") >>> Bag(dog, dog, dog)

bag.multiplicity("cat")  >>> 1
bag.multiplicity("dog") >>> 3
bag.multiplicity("fish")  >>> 0

bag.maxMultiplicity >>> 3 // the multiplicity of "dog"
bag.minMultiplicity >>> 1 // the multiplicity of "cat" or "mouse"

bag.mostCommon >>> Bag(dog, dog, dog)
bag.leastCommon >>> Bag(cat; mouse)

bag union Bag("cat", "fish") >>>  Bag(dog, dog, dog; cat, cat; fish; mouse)
bag intersect Bag("cat", "dog", "dog", "fish") >>>  Bag(dog, dog; cat)
bag diff Bag("cat", "dog", "fish") >>>  Bag(dog, dog; mouse)
bag maxUnion Bag("cat", "dog", "dog", "fish") >>>  Bag(dog, dog; cat; fish; mouse)

bag.withMultiplicity("dog", 1) >>> Bag(dog; cat; mouse)
```

Additionally for mutable bags there are the operations that change the state of that instance of the bag:

```scala
bag += "cat" >>> Bag(dog, dog, dog; cat, cat; mouse)
bag -= "dog" >>> Bag(dog, dog; cat, cat; mouse)
bag += ("mouse"-> 2) >>> Bag(dog, dog; cat, cat; mouse, mouse, mouse)
bag -= ("dog"-> 2) >>> Bag(cat, cat; mouse, mouse, mouse)
bag.add("fish"->2) >>> Bag(cat, cat; mouse, mouse, mouse; fish, fish)
bag.remove("mouse"->2) >>> Bag(cat, cat; mouse; fish, fish)
bag.setMultiplicity("cat"->3) >>> Bag(cat, cat, cat; mouse; fish, fish)
```

All operations of Iterable are also available, for example:

```scala
bag map (_.size) >>> Bag(5; 3, 3, 3, 3)
bag filter (_.size>3) >>> Bag("mouse")
for (animal <- bag) { ... }
...
```


Example: Histogram
------------------
This example shows how to use a `Bag` to build a histogram. Specifically a histogram of the `Math.random()` function called a number of times.

```scala
import scala.collection.mutable.HashBag

// Configuration for the bag using multiplicities of Int elements on default equivalence
implicit val bagConfiguration = HashBag.configuration.ofMultiplicities[Int]

// Create empty histogram
val histogram = HashBag.empty[Int]

val LIMIT = 50

// Add random numbers to the histogram
for (_ <- 1 to LIMIT * LIMIT) {
    histogram += (Math.random() * LIMIT).toInt
}

// Print the contents of the histogram
println(
    s"""
    |Histogram of (Math.random() * LIMIT).toInt over ${LIMIT * LIMIT} executions
    |=================================================================
    | maximum occurrences: ${histogram.maxMultiplicity}
    | minimum occurrences: ${histogram.minMultiplicity}
    |=================================================================""".stripMargin)

for (i <- 0 until LIMIT) {
    val occurrences = histogram.multiplicity(i)
    println(s"${i.toString.padTo(4, ' ')}:  ${"".padTo(occurrences, '|')} $occurrences")
}

println("=================================================================")
```

Bag Configurations
------------------
Any `Bag` implementation is able to handle different bucket implementations and different equivalence groupings.
### Equivalence
Custom equivalences are defined using instances that implement the method `equiv(x,y)` of the `Equiv[T]` trait. Equivalence groupings represent equivalence relations, thus the relation must satisfy:
* reflexive: ``equiv(x,x) == true`  for any `x`
* symmetric: `equiv(x,y) == equiv(y,x)` for any `x` and any `y`
* transitive: if `equiv(x,y) == true && equiv(y,z) == true` then `equiv(x,z) == true`

For both bag implementations (`HashBag` and `TreeBag`) it is necessary to add other traits. The HashBag requires a hashing function (hashed equivalence) and the TreeBag requires an ordering (ordered equivalence). It is possible to have Bags that only depend on equivalence but none has been implemented.

### Bucket representation
There are three basic of representations of bag buckets: MultiplicityBucket, BagOfMultiplicities and ListBucket. The first two are compact representations and the last is a full representation. The full representation keeps all the references to the objects inside. The compact representation only keeps one reference to one of the objects and the multiplicity of that object in the bag. The compact representation provides faster implementations of methods involving querying and transformation.

`MultiplicityBucket` is an implementation of buckets that retain one reference to one of the elements that were added to the bag and it’s multiplicity. This is the most compact representation available for bags and it works only on bags with grouping on equality. For example a the bag `Bag(1, 1, 1; 2; 3, 3)` would have three `MultiplicityBuckets`: one that contains the integer 1 as sentinel and the integer 3 as multiplicity `MultiplicityBucket(1->3)`, one with `MultiplicityBucket(2->1)` and one with `MultiplicityBucket(3->2)`.

`BagOfMultiplicities` is an implementation of buckets that allows custom equivalences while being a compact representation. It is actually a bag that contains only equivalent elements. The bag has a compact representation on equality using `MultiplicityBucket` internally. For example the bag `Bag(‘A’,’A’,’a’;’B’,’b’;’c’)` where upper and lower cases are defined as equivalent, the bag would contain one `BagOfMultiplicities` for {`"A"`,`"A"`,`"a"`}, one for {’B’,’b’} and one for {`"c"`}. And,  the one that contains {`"A"`,`"A"`,`"a"`} will be a bag with the buckets `MultiplicityBucket("A"->2)` and `MultiplicityBucket("a"->1)`.

`ListBucket` is an implementation of buckets that keeps all the references of the elements of the bag in a List. Hence it is a full representation where there is one list for each equivalency grouping. For example the bag `Bag("A", "A", "a", "B", "b"; "c")` will contain the three buckets where each contains one of the following lists: `List("A","A","a")`, `List("B")` or `List("c")`.

* Bag configurations are instances of some implementation of the BagConfiguration trait. Each bag companion object exposes three methods that create bag configurations for it. Those methods are `compact`, `compactWithEquiv` and `keepAll`. Their signature varies depending on the kind of equivalence that is expected (hashed or sorted).
* `Bag.configuration.compact`: configuration for bags with `MultiplicityBucket`.
* `Bag.configuration.compactWithEquiv`: configuration for bags with `BagOfMultiplicities`.
* `Bag.configuration.keepAll`: configuration for bags with `ListBucket`.


### Hashed equivalence

Usually hash table use the hash code and the equality function of an object to place and find it. In Bags object are placed using an equivalence that does not necessarily correspond to equality. In such a case the default hash function will almost certainly not be coherent with the equivalence. Therefore to define the custom equivalence it is also necessary to define it along a coherent hashing function. A coherent hashing function is one that will have the same value for two elements that are equivalent (if `equiv(x,y) == true` then `hash(x)==hash(y)`). To pack the two properties (or functions) together these equivalences are represented with an instance of `Equiv[T] with Hashing[T]`.


For example the case insensitive equivalence on stings, where `"Cat"`, `"cat"` and `"CaT"` are equivalent, can be defined with:
```scala
val stringEquiv =   new Equiv[String] with Hashing[String] {
   def equiv(x: String, y: String): Boolean = x.toLowerCase == y.toLowerCase
   def hash(x: String): Int = x.toLowerCase.hashCode
}
```
A simple way to make sure that the hashing is coherent with the equivalence is to have a representative element function `rep(e)`, such that all elements that are equivalent are mapped to the same element. Then the `equiv(x,y)` function simply returns `rep(x)==rep(y)` and the `hash(x)` returns `rep(x).hashCode`. In the example above the string representation function is the toLowerCase method.

To create configurations for bags with elements of type T with hashed equivalences the signatures are as follows:
* `compact[T]`: Creates a configuration with equivalence defined as equality. The hashing used is the object's hash code. The buckets will be instances of `MultiplicityBucket`.
* `compactWithEquiv[T](equivClass: Equiv[T] with Hashing[T])`: Creates a configuration with a custom equivalence `equivClass`. The buckets will be instances of `BagOfMultiplicities`.
* `keepAll[T]`: Creates a configuration with equivalence defined as equality. The hashing used is the object's hash code. The buckets will be instances of `ListBucket`.
* `keepAll[T](equivClass: Equiv[T] with Hashing[T])`: Creates a configuration with a custom equivalence `equivClass`. The buckets will be instances of `ListBucket`.


### Ordered equivalence
When using ordered trees, such as red-black trees, it is necessary to have an ordering on the elements. Therefore it is possible to take advantage of this existing requirement to define the equivalence at the same time by using only one `Ordering` instance. Note that `Ordering[T]` implements `Equiv[T]` with `equiv(x,y) = compare(x,y) == 0`.

For example the equivalence over strings where two strings are equivalent if and only if their size is the same:
```scala
val stringEquiv =   new Ordering[String] {
   def compare(x: String, y: String): Int = x.size compare y.size
}
```

To implement this function correctly for some equivalence just make sure the for equivalent elements the compare function is reflexive, symmetric, and transitive.

To create configurations for bags with elements of type `T` with ordered equivalences the signatures are as follows:
* `compact[A](implicit equivClass: Ordering[T])`: Creates a configuration with a equivalence/ordering defined on equality. If it is used without the parameters it will try to find an implicit `Ordering[T]` of for that class,  this ordering must define the equivalence as equality. The buckets will be instances of `MultiplicityBucket`.
* `compactWithEquiv[A](equivClass: Ordering[T])(implicit innerOrdering: Ordering[T])`: Creates a configuration with a custom equivalence/ordering equivClass. The buckets will be instances of `BagOfMultiplicities`. It receives an implicit `Ordering[T]` that is used as ordering of the buckets, this ordering must define the equivalence as equality.
* `keepAll[A](implicit equivClass: Ordering[T])`: Creates a configuration with a custom equivalence/ordering `equivClass`. The buckets will be instances of `ListBucket`.

How to use Bag with custom equivalences
---------------------------------------------------------

Custom equivalences on bags are exposed on the `apply`, `mostCommon`, `leastCommon`, `bucketsIterator` and `toString` methods. The other methods yield the same results with any equivalence defined on it, but the execution times may vary with different configurations.

### Hashed Equivalence
Hashed equivalences work on mutable or immutable `HashBags`. The example below shows how to configure bags with custom equivalence. The case insensitive equivalence on stings, where `"Cat"`, `"cat"` and `"CaT"` are equivalent is used.

```scala
// immutable Bag with a hash table containing the bag buckets
import scala.collection.Bag
import scala.collection.immutable.{HashBag=>Bag}
// or mutable Bag with a hash table containing the bag buckets
import scala.collection.mutable.{HashBag=>Bag}

import scala.util.hashing.Hashing

object StringEquiv extends Equiv[String] with Hashing[String] {
   def equiv(x: String, y: String): Boolean = x .toLowerCase == y.toLowerCase
   def hash(x: String): Int = x.toLowerCase.hashCode
}

// Configuration of that keeps one reference to the element and it’s multiplicity and
// puts elements that are not equivalent with respect of StringEquiv in different subbags.
implicit val config = Bag.configuration.compactWithEquiv(StringEquiv)
// or a configuration of that keeps all references to the elements into lists. 
// Where each list contains only equivalent elements with respect to StringEquiv.
implicit config = Bag.configuration.keepAll(StringEquiv)

val bag = Bag("cat", "dog", "Mouse", "Cat", "Dog", "Cat" )

bag.toString() >>> Bag(cat, Cat, Cat; dog, Dog, Mouse)

bag("CAT") >>> Bag("cat", "Cat", "Cat")
bag("DoG") >>> Bag("dog", "Dog")
bag("mouse") >>> Bag("Mouse")
bag("fish") >>> Bag()

bag.mostCommon >>> Bag("cat", "Cat", "Cat")
bag.leastCommon >>> Bag("Mouse")

// List of all one representative element of each bucket
bag.bucketsIterator.map(_.sentinel).toList >>> List("cat", "Dog", "Mouse")

// Other methods that don’t take into account the equivalences still behave the same way
// For example the multiplicity method:
bag.multiplicity("cat") >>> 1
bag.multiplicity("Cat") >>> 2
bag.multiplicity("CAT") >>> 0
```

### Ordered Equivalence
Sorted equivalences work on mutable or immutable `TreeBags`. The example below shows how to configure bags with custom equivalence. The equivalence over strings where two strings are equivalent if and only if their size is the same is used.

```scala
// immutable Bag with a red-black trees containing the bag buckets
import scala.collection.Bag
import scala.collection.immutable.{TreeBag=>Bag}
// or mutable Bag with a red-black trees containing the bag buckets
import scala.collection.mutable.{TreeBag=>Bag}

object StringOrdering extends Ordering[String] {
   def compare(x: String, y: String): Int = x.size compare y.size
}

// Configuration of that keeps one reference to the element and it’s multiplicity 
// and puts elements that are not equivalent with respect of StringOrdering in different sub-bags. 
// Buckets are in the order defined in StringOrdering
implicit val config = Bag.configuration.compactWithEquiv(StringOrdering)
// or a configuration of that keeps all references to the elements into lists. 
// Where each list contains only equivalent elements with respect to StringOrdering. 
// Buckets are in the order defined in StringOrdering.
implicit config = Bag.configuration.keepAll(StringOrdering).

val bag = Bag("cat", "mouse","fish", "elephant", "dog", "bear" )

bag.toString() >>> Bag(cat, dog; bear, fish; mouse; elephant)

bag("cat") >>> Bag("cat", "dog")
bag("ooo") >>> Bag("cat", "dog")
bag("xxxx") >>> Bag("fish", "bear")

bag.mostCommon >>>  Bag("cat", "dog", "fish", "bear") 
bag.leastCommon >>> Bag( "mouse", "elephant") 

// List with the sizes of the strings in the bag
bag.bucketsIterator.map(_.sentinel.size).toList >>> List(3, 4, 5, 8)
```
