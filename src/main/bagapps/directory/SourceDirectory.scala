package bagapps.directory

import java.io.{File => JFile}

import scala.collection.immutable._
import scala.collection
import scala.util.hashing.Hashing


class SourceDirectory private(root: JFile) {

  private def getFilesInBag(equivHash: Equiv[File] with Hashing[File]): Bag[File] = {
    implicit val bagConfiguration = Bag.configuration.ofBagOfMultiplicities[File](equivHash) // receives equivHashImplicit implicitly as Equiv[File] and Hashing[File]

    val b = Bag.newBuilder[File]

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
            b += File(dir, name, ext)
          }
      }
    }

    putFilesInBag(root)

    b.result()
  }

  lazy val filesByName: collection.Bag[File] = getFilesInBag(File.FileNameEquiv)

  lazy val filesByDirectory: collection.Bag[File] = getFilesInBag(File.DirectoryEquiv)

  lazy val filesByExtension: collection.Bag[File] = getFilesInBag(File.ExtensionEquiv)

  override def toString: String = root.getAbsolutePath
}

object SourceDirectory {

  def apply(root: String) = new SourceDirectory(new JFile(root))

  def apply(root: JFile) = new SourceDirectory(root)

}