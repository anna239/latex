import latex.calculator.TeXCalculator
import latex.formulaextractor.FormulaExtractor
import latex.parser.ExpressionParser


object Main extends App {

  var f1:ExpressionParser = new ExpressionParser

  val c = new TeXCalculator()
  c.setVal("a", 3)
  c.setVal("b", 4)
  println(c.calculate(f1.parse("a*b")))

  println(new FormulaExtractor().extract("The quadratic formula is $-b \\pm \\sqrt{b^2 - 4ac} \\over 2a$").toList)
}