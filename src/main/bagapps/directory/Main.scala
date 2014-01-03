package bagapps.directory

import java.io.{File => JFile}
import scala.collection.Bag

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

    val filesByName: Bag[File] = directory.filesByName
    val filesByExtension: Bag[File] = directory.filesByExtension
    val filesByDirectory: Bag[File] = directory.filesByDirectory

    println(
      s"""
         |SOURCE FOLDER
         |path: $directory
         |========================
         |extension    count
         |------------------------
         |.scala       ${filesByExtension(File.withExtension("scala")).size}
         |.xml         ${filesByExtension(File.withExtension("xml")).size}
         |.class       ${filesByExtension(File.withExtension("class")).size}
         |.jar         ${filesByExtension(File.withExtension("jar")).size}
         |.sbt         ${filesByExtension(File.withExtension("sbt")).size}
         |.properties  ${filesByExtension(File.withExtension("properties")).size}
         |*other       ${
        (filesByExtension -* File.withExtension("scala") -* File.withExtension("xml") -* File.withExtension("class")
          -* File.withExtension("jar") -* File.withExtension("sbt") -* File.withExtension("properties")).size
      }
         |========================
         |List of .scala files
         |------------------------
         |${filesByExtension(File.withExtension("scala")).take(10).mkString("\n")}
         |.....
         |========================
         |Most common file names
         |------------------------
         |${filesByName.mostCommon.distinct.map(_.name).mkString("\n")}
         |========================
         |Directory with most files
         |------------------------
         |${filesByDirectory.mostCommon.distinct.map(_.directory).mkString("\n")}
         |number of files in directory: ${filesByDirectory.mostCommon.size}
         |========================
         | Files by extension: ${filesByExtension}
         | Files by directory: ${filesByDirectory}
         | Files by name:      ${filesByName}
         |========================
      """.stripMargin)


  }


}
