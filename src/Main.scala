import latex.calculator.TeXCalculator
import latex.parser.ExpressionParser


object Main extends App {

  var f1:ExpressionParser = new ExpressionParser

  val c = new TeXCalculator()
  c.setVal("a", 3)
  c.setVal("b", 4)
  print(c.calculate(f1.parse("a*b")))
}