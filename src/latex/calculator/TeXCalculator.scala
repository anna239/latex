package latex.calculator

import collection.mutable
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.Plus

case class TeXCalculator() {

  val values = new mutable.HashMap[String, TeXValue[Double]]()

  def setVal(name: String, value: Int) {
    values += ((name, new TeXValue[Int](value)))
  }

  def calculate(formula: Node): Double = {
    formula match {
      case VarNode(name) => values(name).value
      case IntLiteralNode(value) => value
    }
  }

  def evalOp(l: ExpressionNode, r: ExpressionNode, op: BinaryOperation) = {
    op match {
      case Plus => calculate(l) + calculate(r)
      case Minus => calculate(l) - calculate(r)
      case Multiplication => calculate(l) * calculate(r)
      case Division => calculate(l) / calculate(r)
      case Power => math.pow(calculate(l), calculate(r))
    }
  }

}
