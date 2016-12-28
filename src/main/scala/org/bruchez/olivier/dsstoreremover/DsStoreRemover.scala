package org.bruchez.olivier.dsstoreremover

import scala.util._

object DsStoreRemover {
  def main(args: Array[String]): Unit =
    Try(Arguments(args)) match {
      case Failure(_) ⇒
        println("Usage: DsStoreRemover <directory to clean> <trash directory>")
        System.exit(-1)
      case Success(arguments) ⇒
        DsStoreRemover(arguments).clean()
    }
}

case class DsStoreRemover(arguments: Arguments) {
  def clean(): Unit = {
    val allFiles = Files.filesInDirectory(arguments.directoryToClean, recursive = true, includeDirectories = false)
    val trashDirectoryPrefixPath = arguments.trashDirectory.getCanonicalPath

    for {
      file ← allFiles
      if !file.getCanonicalPath.startsWith(trashDirectoryPrefixPath)
      if Files.isMacOsMetadataFile(file)
      destinationFile = Files.nonExistingFile(arguments.trashDirectory, file.getName)
    } {
      if (arguments.readOnly) {
        println(s"File '${file.getCanonicalPath}' would be moved to '${destinationFile.getCanonicalPath}' (read only)")
      } else {
        println(s"Moving '${file.getCanonicalPath}' to '${destinationFile.getCanonicalPath}'")
        file.renameTo(destinationFile)
      }
    }
  }
}
