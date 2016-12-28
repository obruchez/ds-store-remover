package org.bruchez.olivier

import java.io.File

object DsStoreRemover {
  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println("Usage: DsStoreRemover <directory to clean> <trash directory>")
      System.exit(-1)
    }

    DsStoreRemover(new File(args(0)), new File(args(1))).clean()
  }
}

case class DsStoreRemover(directoryToClean: File, trashDirectory: File) {
  def clean(): Unit = {
    val allFiles = Files.filesInDirectory(directoryToClean, recursive = true, includeDirectories = false)
    val trashDirectoryPrefixPath = trashDirectory.getAbsolutePath

    for {
      file ← allFiles
      if !file.getAbsolutePath.startsWith(trashDirectoryPrefixPath)
      if Files.isMacOsMetadataFile(file)
      destinationFile = Files.nonExistingFile(trashDirectory, file.getName)
    } {
      file.renameTo(destinationFile)
    }
  }
}
