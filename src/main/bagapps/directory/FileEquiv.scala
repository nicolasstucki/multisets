package bagapps.directory


object FileEquiv {

  object ExtensionEquiv extends Equiv[File] {
    def equiv(x: File, y: File): Boolean = x.ext == y.ext
  }

  object FileNameEquiv extends Equiv[File] {
    def equiv(x: File, y: File): Boolean = x.name == y.name
  }

  object DirEquiv extends Equiv[File] {
    def equiv(x: File, y: File): Boolean = x.dir == y.dir
  }

  object DirDepthEquiv extends Equiv[File] {
    def equiv(x: File, y: File): Boolean = x.directoryDepth == y.directoryDepth
  }

}