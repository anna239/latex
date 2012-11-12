package latex.calculator

import org.scilab.forge.jlatexmath.TeXFormula
import collection.mutable.HashMap
import collection.mutable
import latex.structure._
import sun.plugin2.main.server.Plugin
import latex.structure.BinOpNode
import latex.calculator.TeXValue
import latex.structure.VarNode

case class TeXCalculator() {

  val values = new mutable.HashMap[String, TeXValue[Int]]()

  def setVal(name: String, value: Int) {
    values += ((name, new TeXValue[Int](value)))
    print(values)
  }

  def calculate(formula: Node):Double = {
    formula match {
      case VarNode(name) => values(name).value
      case BinOpNode(left, right, op) => evalOp(left, right, op)
    }
  }

  def evalOp(l: ExpressionNode, r: ExpressionNode, op: Operation) = {
    op match {
      case s: Plus => calculate(l) + calculate(r)
    }
  }

}
