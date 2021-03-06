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

  "Parser" should "be able to parse integer and floating point literals" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.setVal("a", 2)
    calculator.calculate(parser.parse("a*3")) should equal (6)
    calculator.calculate(parser.parse("a-39")) should equal (2-39)
    calculator.calculate(parser.parse("31233/a")) should equal (31233.0/2)
    calculator.calculate(parser.parse("2.3")) should equal (2.3)
    calculator.calculate(parser.parse("a+25.2")) should equal (2+25.2)
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

  "Parser" should "be able to parse simple predefined functions" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.calculate(parser.parse("\\sqrt {4}")) shouldBe 2
    calculator.calculate(parser.parse("\\sqrt {22 - 18}")) shouldBe 2
    calculator.calculate(parser.parse("\\sqrt 4")) shouldBe 2
    val x = 1.0
    val y = 2.0
    val z = 3.0
    calculator.setContext(Array("x", "y", "z"), Array(x, y, z))
    calculator.calculate(parser.parse("\\frac {x+y-z} {2^x}")) shouldBe ((x + y - z)/ math.pow(2, x))
    calculator.calculate(parser.parse("\\frac {2} {3}")) shouldBe (2.0 / 3.0)
    calculator.calculate(parser.parse("\\sqrt [x + y] {10}")) shouldBe (math.pow(10, 1.0/ (x + y)))
    calculator.calculate(parser.parse("\\sqrt {2} {33}")) shouldBe (math.pow(33, 1.0 / 2))
    calculator.calculate(parser.parse("\\sqrt [1+3] 5")) shouldBe (math.pow(5, 1.0 / 4))
    calculator.calculate(parser.parse("\\sqrt [2] {4+0}")) shouldBe (math.pow(4, 1.0 / 2))
    calculator.calculate(parser.parse("\\frac {1+2} z")) shouldBe (3.0 / z)
    calculator.calculate(parser.parse("\\sqrt [3] {x+y} + \\sqrt {3}")) shouldBe (math.pow(x + y, 1.0 / 3) + math.sqrt(3.0))
  }

  "Parser" should "parse greek letters properly" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    parser.parse("\\rho + \\varsigma")
    calculator.setVal("r", 3)
    calculator.calculate(parser.parse("\\pi * r^2 / 2")) shouldBe (math.Pi * 9 / 2)
  }

  "Parser" should "parse trigonometric functions" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.calculate(parser.parse("(\\cos^2 2) + (\\sin^2 2) ")) shouldBe 1.0
  }

  "Parser" should "parse sum" in {
    val parser = new ExpressionParser
    val calculator = new TeXCalculator
    calculator.calculate(parser.parse("\\sum_{i=0}^{10} i")) shouldBe (55)
  }


}
