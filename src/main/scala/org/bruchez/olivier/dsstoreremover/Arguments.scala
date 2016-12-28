package org.bruchez.olivier.dsstoreremover

import java.io.File

case class Arguments(
  directoryToClean: File = new File("."),
    trashDirectory: File = new File("."),
    readOnly: Boolean = false
) {

}

object Arguments {
  def apply(args: Array[String]): Arguments = {
    @annotation.tailrec
    def fromArgs(args: List[String], arguments: Arguments): Arguments =
      args match {
        case Nil ⇒
          arguments
        case directoryToClean :: trashDirectory :: Nil ⇒
          arguments.copy(directoryToClean = new File(directoryToClean), trashDirectory = new File(trashDirectory))
        case argument :: remainingArguments ⇒
          val (newArguments, argumentsToParse) = argument match {
            case ReadOnlyArgument ⇒
              (arguments.copy(readOnly = true), remainingArguments)
            case _ ⇒
              throw new IllegalArgumentException(s"Unexpected argument: $argument")
          }

          fromArgs(argumentsToParse, newArguments)
      }

    fromArgs(args.toList, Arguments())
  }

  private val ReadOnlyArgument = "-readonly"
}
