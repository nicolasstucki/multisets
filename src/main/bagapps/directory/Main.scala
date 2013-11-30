package bagapps.directory

import java.io.{File => JFile}

object Main {

  def main(args: Array[String]) {

    if (args.size != 1) {
      println("Parameter not defined. Parameter must point to a source directory.")
      return
    }

    val rootDirectory = new JFile(args(0))

    if (!rootDirectory.isDirectory) {
      println("Error. Parameter must point to a source directory.")
      return
    }

    val directory = SourceDirectory(rootDirectory)

    println(
      s"""
         |SOURCE FOLDER
         |path: $directory
         |========================
         |extension    count
         |------------------------
         |.scala       ${directory.filesByExtension(File.withExtension("scala")).size}
         |.xml         ${directory.filesByExtension(File.withExtension("xml")).size}
         |.class       ${directory.filesByExtension(File.withExtension("class")).size}
         |.jar         ${directory.filesByExtension(File.withExtension("jar")).size}
         |.sbt         ${directory.filesByExtension(File.withExtension("sbt")).size}
         |.properties  ${directory.filesByExtension(File.withExtension("properties")).size}
         |*other       ${(directory.filesByExtension -* File.withExtension("scala") -* File.withExtension("xml") -* File.withExtension("class")
        -* File.withExtension("jar") -* File.withExtension("sbt") -* File.withExtension("properties")).size}
         |========================
         |List of .scala files
         |------------------------
         |${directory.filesByExtension(File.withExtension("scala")).take(10).mkString("\n")}
         |.....
         |========================
         |Most common file names
         |------------------------
         |${directory.filesByName.mostCommon.distinct.map(_.name).mkString("\n")}
         |========================
         |Directory with most files
         |------------------------
         |${directory.filesByDirectory.mostCommon.distinct.map(_.directory).mkString("\n")}
         |number of files in directory: ${directory.filesByDirectory.mostCommon.size}
         |========================
         | Files by extension: ${directory.filesByExtension}
         | Files by directory: ${directory.filesByDirectory}
         | Files by name:      ${directory.filesByName}
         |========================
      """.stripMargin)


  }


}
