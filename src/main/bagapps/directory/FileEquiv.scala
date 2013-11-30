package bagapps.directory

import scala.util.hashing.Hashing


object FileEquiv {

  object ExtensionEquiv extends Ordering[File] with Hashing[File] {
    def compare(x: File, y: File): Int = x.ext compareTo y.ext

    def hash(x: File): Int = x.ext.hashCode
  }

  object FileNameEquiv extends Ordering[File] with Hashing[File] {
    def compare(x: File, y: File): Int = x.name compareTo y.name

    def hash(x: File): Int = x.name.hashCode
  }

  object DirectoryEquiv extends Ordering[File] with Hashing[File] {
    def compare(x: File, y: File): Int = x.directory compareTo y.directory

    def hash(x: File): Int = x.directory.hashCode
  }

}