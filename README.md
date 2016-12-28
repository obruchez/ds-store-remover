# ds-store-remover

A program that removes all .DS_Store and ._* files (i.e. macOS / Finder files) from a directory and its sub-directories. It doesn't delete any file, but moves them all to a single "trash" directory, renaming the files to avoid name collisions as needed.
