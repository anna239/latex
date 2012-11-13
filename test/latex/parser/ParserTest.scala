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

  "Parser" should "be able to parse comninations of simple operations" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.setVal("a", 3)
    calculator.setVal("b", 4)
    calculator.calculate(parser.parse("a+b-a")) should equal (4)
    calculator.calculate(parser.parse("a*b-a")) should equal (3*4-3)
    calculator.calculate(parser.parse("a/b-a")) should equal (3.0/4-3)
  }

  "Parser" should "respect the priority of simple operations" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.setVal("a", 2)
    calculator.setVal("b", 4)
    calculator.calculate(parser.parse("a+b*a-b")) should equal (2+4*2-4)
    calculator.calculate(parser.parse("a*a+a/b")) should equal (2*2+2.0/4)
    calculator.calculate(parser.parse("a+a/b*b-b*a+b")) should equal (2+2.0/4*4-4*2+4)
  }

  "Parser" should "be able to parse integer literals" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.setVal("a", 2)
    calculator.calculate(parser.parse("a*3")) should equal (6)
    calculator.calculate(parser.parse("a-39")) should equal (2-39)
    calculator.calculate(parser.parse("31233/a")) should equal (31233.0/2)
  }

  "Parser" should "be able to parse power expressions" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.setVal("a", 2)
    calculator.calculate(parser.parse("a^3")) should equal (8)
    calculator.calculate(parser.parse("a-2^4")) should equal (2-16)
  }

  "Parser" should "be able to parse simple parenthesized and bracketed expressions" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.setVal("a", 2)
    calculator.calculate(parser.parse("5*(a+2)")) should equal (20)
    calculator.calculate(parser.parse("[a+2]^3")) should equal (64)
    calculator.calculate(parser.parse("(a+2)*(a+3)")) should equal (20)
    calculator.calculate(parser.parse("((a+2)*(a+3)) *(2)")) should equal (40)
    calculator.calculate(parser.parse("([a+2]*(a+3)) *[2]")) should equal (40)
  }
}
