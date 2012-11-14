package latex.calculator

import collection.mutable
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.Plus

case class TeXCalculator() {

  val values = new mutable.HashMap[String, TeXValue[Double]]()
  values += (("\\pi", new TeXValue[Double](scala.math.Pi)))
  values += (("e", new TeXValue[Double](scala.math.E)))

  def getVal(name: String): Double = {
    values.getOrElse(name, new TeXValue[Double](0)).value
  }

  def setVal(name: String, value: Double) {
    values += ((name, new TeXValue[Double](value)))
  }

  def setContext(names: Array[String], vals: Array[Double]) {
    names.zip(vals).foreach {
      case (n, v) => values += ((n, new TeXValue[Double](v)))
    }
  }

  def calculate(formula: Node): Double = {
    formula match {
      case VarNode(name) => values(name).value
      case IntLiteralNode(value) => value
      case DoubleLiteralNode(value) => value
      case BinOpNode(left, right, op) => evalBinOp(left, right, op)
      case FunctionNode(function, arguments) => evalFunction(function, arguments)
      case UnOpNode(op, operand) => evalUnaryOp(op, operand)
      case SumNode(sumVar, from, to, body) => evalSumNode(sumVar, calculate(from).toInt, calculate(to).toInt, body)
    }
  }

  def valsRequired(formula: Node): mutable.Set[String] = {
    formula match {
      case VarNode(name) => mutable.Set(name)
      case IntLiteralNode(value) => mutable.Set.empty
      case DoubleLiteralNode(value) => mutable.Set.empty
      case BinOpNode(left, right, op) => valsRequired(left).union(valsRequired(right))
      case FunctionNode(function, arguments) => arguments.foldLeft(mutable.Set.empty: mutable.Set[String])({
        case (a, b) => valsRequired(b).union(a)
      })
      case UnOpNode(op, operand) => valsRequired(operand)
      case SumNode(sumVar, from, to, body) => valsRequired(body).diff(valsRequired(sumVar))
      case _ => mutable.Set.empty
    }
  }

  def valsRequiredAsArray(formula: Node): Array[String] = {
    valsRequired(formula).toArray
  }

  def evalSumNode(sumVar: VarNode, from: Int, to: Int, body: ExpressionNode): Double = {
    var result: Double = 0
    for (i <- from to to) {
      values += ((sumVar.name, new TeXValue[Double](i)))
      result += calculate(body)
      values.remove(sumVar.name)
    }
    result
  }

  private def evalUnaryOp(op: UnaryOperation, operand: ExpressionNode): Double = {
    op match {
      case UnaryMinus => -calculate(operand)
    }
  }

  private def evalFunction(func: Function, args: List[ExpressionNode]): Double = {
    //todo zeros
    func match {
      case SqrtFunction => math.sqrt(calculate(SqrtFunction.getArgument(args)))
      case SqrtnFunction => math.pow(calculate(SqrtnFunction.getArgument(args)), 1.0 / calculate(SqrtnFunction.getDegree(args)))
      case FracFunction => calculate(FracFunction.getNominator(args)) / calculate(FracFunction.getDenominator(args))
      case SinFunction => math.sin(calculate(SinFunction.getArgument(args)))
      case CosFunction => math.cos(calculate(CosFunction.getArgument(args)))
      case TanFunction => math.tan(calculate(TanFunction.getArgument(args)))
      case CotFunction => 1 / math.tan(calculate(CotFunction.getArgument(args)))
      case ArcSinFunction => math.asin(calculate(ArcSinFunction.getArgument(args)))
      case ArcCosFunction => math.acos(calculate(ArcCosFunction.getArgument(args)))
      case ArcTanFunction => math.atan(calculate(ArcTanFunction.getArgument(args)))
      case ArcCotFunction => math.atan(1 / calculate(ArcCotFunction.getArgument(args)))
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
