package latex.parser

import org.scalatest._
import latex.calculator.TeXCalculator

class ParserTest extends FlatSpec with ShouldMatchers {

  "Parser" should "be able to parse simple arithmetic operations" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.setVal("a", 3)
    calculator.setVal("b", 4)
    calculator.calculate(parser.parse("a+b")) should equal (7)
    calculator.calculate(parser.parse("a-b")) should equal (-1)
    calculator.calculate(parser.parse("a*b")) should equal (12)
    calculator.calculate(parser.parse("a/b")) should equal (0.75)
    calculator.setVal("b", 7)
    calculator.calculate(parser.parse("a+b")) should equal (10)
    calculator.calculate(parser.parse("a-b")) should equal (-4)
    calculator.calculate(parser.parse("a*b")) should equal (21)
    calculator.calculate(parser.parse("a/b")) should equal (3.0/7)
  }

}
