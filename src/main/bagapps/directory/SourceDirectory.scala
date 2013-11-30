package bagapps.directory

import java.io.{File => JFile}
import scala.collection.mutable


class SourceDirectory private(root: JFile) {

  private implicit def bucketFactory(implicit equiv: Ordering[File]) = scala.collection.mutable.SortedBagBucketFactory.ofVectors[File]

  private def putFilesInBag(bag: mutable.Bag[File]) {
    def putFilesInBag(directory: JFile) {
      directory.listFiles().foreach {
        case dir if dir.isDirectory =>
          putFilesInBag(dir)
        case file if file.isFile =>
          val index = file.getName.lastIndexOf('.')
          if (index != -1 && !file.getName.startsWith(".")) {
            val name = file.getName.substring(0, index)
            val ext = file.getName.substring(index + 1)
            val dir = file.getParent
            bag += File(dir, name, ext)
          }
      }
    }

    putFilesInBag(root)
  }

  lazy val filesByName: collection.Bag[File] = {
    implicit val equiv = File.FileNameEquiv
    val bag = mutable.Bag.empty[File]
    putFilesInBag(bag)
    bag
  }

  lazy val filesByDirectory: collection.Bag[File] = {
    implicit val equiv = File.DirectoryEquiv
    val bag = mutable.Bag.empty[File]
    putFilesInBag(bag)
    bag
  }

  lazy val filesByExtension: collection.Bag[File] = {
    implicit val equiv = File.ExtensionEquiv
    val bag = mutable.Bag.empty[File]
    putFilesInBag(bag)
    bag
  }

  override def toString: String = root.getAbsolutePath
}

object SourceDirectory {

  def apply(root: String) = new SourceDirectory(new JFile(root))

  def apply(root: JFile) = new SourceDirectory(root)

}