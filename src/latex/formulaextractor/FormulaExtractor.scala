package latex.formulaextractor

import java.io.File


class FormulaExtractor {
  def extract(doc: String): Array[String] = {
    (" " + doc).replaceAll("\\\\[$]", "").split("([$]{2})|[$]").zipWithIndex.filter {
      case (s, i) => i % 2 == 1
    }.map {
      case (s, i) => s
    }
  }

  def extract(file: File): Array[String] = {
    var s = ""
    for {line <- io.Source.fromFile(file, "utf-8").getLines()}
      s += line + " "
    extract(s)
  }
}