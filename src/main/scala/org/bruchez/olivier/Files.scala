package org.bruchez.olivier

import java.io.File
import scala.annotation.tailrec

object Files {
  def filesInDirectory(directory: File, recursive: Boolean, includeDirectories: Boolean): Seq[File] = {
    val (directories, files) = Option(directory.listFiles()).fold(Seq[File]())(_.toSeq).partition(_.isDirectory)
    val subDirectoriesAndFiles =
      if (recursive) directories.flatMap(filesInDirectory(_, recursive = true, includeDirectories)) else Seq()
    (if (includeDirectories) directories else Seq()) ++ files ++ subDirectoriesAndFiles
  }

  def isMacOsMetadataFile(file: File): Boolean = {
    val filename = file.getName

    lazy val isDsStoreFile = filename == MacOsDsStoreFilename

    lazy val isMetadataFile = filename.startsWith(MacOsMetadataFilePrefix) && {
      val baseFile = new File(file.getParentFile, filename.substring(MacOsMetadataFilePrefix.length))
      baseFile.exists()
    }

    isDsStoreFile || isMetadataFile
  }

  private val MacOsDsStoreFilename = ".DS_Store"
  private val MacOsMetadataFilePrefix = "._"

  def nonExistingFile(directory: File, baseFilename: String): File = {
    @tailrec
    def nonExistingFile(index: Int): File = {
      val suffix = if (index <= 0) "" else s".$index"

      val fileToTest = new File(directory, baseFilename + suffix)

      if (!fileToTest.exists()) fileToTest else nonExistingFile(index + 1)
    }

    nonExistingFile(index = 0)
  }
}
