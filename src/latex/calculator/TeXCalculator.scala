package latex.calculator

import collection.mutable
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.Plus

case class TeXCalculator() {

  val values = new mutable.HashMap[String, TeXValue[Double]]()
  values += (("pi", new TeXValue[Double](3.14)))

  def setVal(name: String, value: Double) {
    values += ((name, new TeXValue[Double](value)))
  }

  def setContext(names: Array[String], vals:Array[Double]) {
    names.zip(vals).foreach {case (n, v) => values += ((n, new TeXValue[Double](v)))}
  }

  def calculate(formula: Node): Double = {
    formula match {
      case VarNode(name) => values(name).value
      case IntLiteralNode(value) => value
      case DoubleLiteralNode(value) => value
      case BinOpNode(left, right, op) => evalBinOp(left, right, op)
      case FunctionNode(function, arguments) => evalFunction(function, arguments)
      case UnOpNode(op, operand) => evalUnaryOp(op, operand)
    }
  }

  private def evalUnaryOp(op: UnaryOperation, operand: ExpressionNode): Double = {
    op match {
      case UnaryMinus => -calculate(operand)
    }
  }

  private def evalFunction(func: Function, args: List[ExpressionNode]): Double = {
    func match {
      case SqrtFunction => math.sqrt(calculate(SqrtFunction.getArgument(args)))
      case SqrtnFunction => math.pow(calculate(SqrtnFunction.getArgument(args)), calculate(SqrtnFunction.getDegree(args)))
      case FracFunction => calculate(FracFunction.getNominator(args)) / calculate(FracFunction.getDenominator(args))
    }
  }

  private def evalBinOp(l: ExpressionNode, r: ExpressionNode, op: BinaryOperation): Double = {
    op match {
      case Plus => calculate(l) + calculate(r)
      case Minus => calculate(l) - calculate(r)
      case Multiplication => calculate(l) * calculate(r)
      case Division => calculate(l) / calculate(r)
      case Power => math.pow(calculate(l), calculate(r))
    }
  }

}
