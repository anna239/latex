package latex.formulaextractor


class FormulaExtractor {
  def extract(doc: String): Array[String] = {
    doc.replaceAll("\\\\[$]", "").split("([$]{2})|[$]").zipWithIndex.filter {
      case (s, i) => i % 2 == 1
    }.map {
      case (s, i) => s
    }
  }
}
