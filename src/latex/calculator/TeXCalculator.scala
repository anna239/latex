package latex.calculator

import collection.mutable
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.Plus

case class TeXCalculator() {

  val values = new mutable.HashMap[String, TeXValue[Int]]()

  def setVal(name: String, value: Int) {
    values += ((name, new TeXValue[Int](value)))
  }

  def calculate(formula: Node): Double = {
    formula match {
      case VarNode(name) => values(name).value
      case BinOpNode(left, right, op) => evalOp(left, right, op)
      case IntLiteral(value) => value
    }
  }

  def evalOp(l: ExpressionNode, r: ExpressionNode, op: Operation) = {
    op match {
      case Plus => calculate(l) + calculate(r)
      case Minus => calculate(l) - calculate(r)
      case Multiplication => calculate(l) * calculate(r)
      case Division => calculate(l) / calculate(r)
    }
  }

}
