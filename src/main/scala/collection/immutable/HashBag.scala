package scala.collection.immutable

import scala.collection._
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.DefaultEntry
import scala.{collection, Some}
import scala.Some
import scala.Some
import scala.collection.generic

class HashBag[A] private[collection](contents: mutable.HashTable.Contents[A, mutable.DefaultEntry[A, immutable.BagBucket[A]]])(implicit protected val bagConfiguration: immutable.HashedBagConfiguration[A])
  extends immutable.Bag[A]
  with immutable.BagLike[A, immutable.HashBag[A]]
  with mutable.HashTable[A, mutable.DefaultEntry[A, immutable.BagBucket[A]]]
  with Serializable {


  initWithContents(contents)

  type Entry = mutable.DefaultEntry[A, immutable.BagBucket[A]]

  override protected[this] def newBuilder: mutable.BagBuilder[A, HashBag[A]] = HashBag.newBuilder

  override def empty: immutable.HashBag[A] = immutable.HashBag.empty[A](bagConfiguration)


  override protected def elemEquals(key1: A, key2: A): Boolean = bagConfiguration.equiv(key1, key2)

  override protected def elemHashCode(key: A): Int = bagConfiguration.equivClass.hash(key)

  override def getBucket(elem: A): Option[BagBucket] = {
    val e = findEntry(elem)
    if (e eq null) None
    else Some(e.value)
  }

  protected def updatedBucket(bucket: BagBucket): HashBag[A] = {
    val bag = new HashBag.HashBagBuilder(hashTableContents)(bagConfiguration)
    bag.updateBucket(bucket)
    bag.result()
  }

  def bucketsIterator: Iterator[BagBucket] = entriesIterator.map(_.value)

  protected def createNewEntry[B](key: A, value: B): mutable.DefaultEntry[A, immutable.BagBucket[A]] = new Entry(key, value.asInstanceOf[immutable.BagBucket[A]])

}


object HashBag extends generic.ImmutableHashedBagFactory[immutable.HashBag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A]): CanBuildFrom[Coll, A, immutable.HashBag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: immutable.HashedBagConfiguration[A]): immutable.HashBag[A] = new immutable.HashBag[A](null)

  override def newBuilder[A](implicit bagConfiguration: HashedBagConfiguration[A]): mutable.BagBuilder[A, HashBag[A]] = new HashBagBuilder[A](null)(bagConfiguration)

  final private class HashBagBuilder[A](contents: mutable.HashTable.Contents[A, mutable.DefaultEntry[A, immutable.BagBucket[A]]])(bagConfiguration: immutable.HashedBagConfiguration[A])
    extends immutable.HashBag[A](contents)(bagConfiguration)
    with mutable.BagBuilder[A, HashBag[A]] {

    def +=(elem: A): this.type = add(elem, 1)

    def clear(): Unit = clearTable()

    def result(): HashBag[A] = this.asInstanceOf[HashBag[A]]

    def add(elem: A, count: Int): this.type = addBucket(bagConfiguration.newBuilder(elem).add(elem, count).result())

    def addBucket(bucket: collection.BagBucket[A]): this.type = getBucket(bucket.sentinel) match {
      case Some(bucket2) => updateBucket(bagConfiguration.bucketFrom(bucket, bucket2))
      case None => updateBucket(bagConfiguration.bucketFrom(bucket))
    }

    def updateBucket(bucket: immutable.BagBucket[A]): this.type = {
      removeEntry(bucket.sentinel)
      addEntry(createNewEntry(bucket.sentinel, bucket))
      this
    }
  }

}