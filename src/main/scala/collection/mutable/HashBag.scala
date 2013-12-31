package scala.collection.mutable


import scala.collection._
import scala.collection.generic.CanBuildFrom
import scala.Some


final class HashBag[A] private[collection](contents: mutable.HashTable.Contents[A, mutable.DefaultEntry[A, mutable.BagBucket[A]]])(implicit protected val bagConfiguration: mutable.HashedBagConfiguration[A])
  extends mutable.Bag[A]
  with mutable.BagLike[A, mutable.HashBag[A]]
  with mutable.HashTable[A, mutable.DefaultEntry[A, mutable.BagBucket[A]]]
  with Serializable {


  initWithContents(contents)

  type Entry = mutable.DefaultEntry[A, mutable.BagBucket[A]]

  override def empty: mutable.HashBag[A] = mutable.HashBag.empty[A](bagConfiguration)

  override def clear() {
    clearTable()
  }

  override protected def elemHashCode(key: A): Int = bagConfiguration.hash(key)

  def getBucket(elem: A): Option[BagBucket[A]] = {
    val e = findEntry(elem)
    if (e eq null) None
    else Some(e.value)
  }


  def updateBucket(bucket: mutable.BagBucket[A]): this.type = {
    addEntry(createNewEntry(bucket.sentinel, bucket))
    this
  }

  def addedBucket(bucket: collection.BagBucket[A]): mutable.HashBag[A] = new mutable.HashBag[A](hashTableContents) addBucket bucket

  def bucketsIterator: Iterator[BagBucket[A]] = entriesIterator.map(_.value)

  protected def createNewEntry[B](key: A, value: B): mutable.DefaultEntry[A, mutable.BagBucket[A]] = new Entry(key, value.asInstanceOf[mutable.BagBucket[A]])

}

object HashBag extends generic.MutableHashedBagFactory[mutable.HashBag] {

  implicit def canBuildFrom[A](implicit bagConfiguration: mutable.HashedBagConfiguration[A]): CanBuildFrom[Coll, A, mutable.HashBag[A]] = bagCanBuildFrom[A]

  def empty[A](implicit bagConfiguration: mutable.HashedBagConfiguration[A]): mutable.HashBag[A] = new mutable.HashBag[A](null)
}