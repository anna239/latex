package latex.parser

import org.scalatest._
import latex.calculator.TeXCalculator

class ParserTest extends FlatSpec with ShouldMatchers {

  "Parser" should "be able to parse plus operation" in {
    var f1:ExpressionParser = new ExpressionParser
    val c = new TeXCalculator()
    c.setVal("a", 3)
    c.setVal("b", 4)
    c.calculate(f1.parse("a+b")) should equal (7)
    c.setVal("b", 7)
    c.calculate(f1.parse("a+b")) should equal (10)
  }

}
