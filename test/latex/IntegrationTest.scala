package latex

import calculator.TeXCalculator
import formulaextractor.FormulaExtractor
import org.scalatest._
import java.io.File
import parser.ExpressionParser

class IntegrationTest extends FlatSpec with ShouldMatchers {
  "ScaLaTeX" should "be able to parse and calculate simple binome formula from some sample file" in {
    val extractor = new FormulaExtractor
    val formulae = extractor.extract(new File("testData/simpleExamples/sample.tex"))
    val calculator = new TeXCalculator
    val binomeFormula = formulae(4)
    val parser = new ExpressionParser
    val node = parser.parse(binomeFormula)
    calculator.calculate(node) should equal (4)
  }
}
