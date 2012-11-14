package latex.structure

abstract sealed case class Function(name: String)

object Function {
  val mathFunctions = List(SinFunction, CosFunction, TanFunction, CotFunction, ArcSinFunction,
    ArcCosFunction, ArcTanFunction, ArcCotFunction)
  val predefinedFunctions = List(SqrtFunction, SqrtnFunction, FracFunction) ++ mathFunctions

  def getPredefinedFunction(name: String, args: Int): Function = {
    predefinedFunctions.find({
      (f: FixedArgumentsFunction) =>
        name == f.name && args == f.argumentsCount
    }).getOrElse(throw new IllegalStateException("Unknown function: " + name))
  }
}

abstract sealed case class FixedArgumentsFunction(override val name: String, argumentsCount: Int) extends Function(name)

abstract sealed case class OneArgumentFunction(override val name: String) extends FixedArgumentsFunction(name, 1) {
  def getArgument(arguments: List[ExpressionNode]):ExpressionNode = arguments(0)
}

case object SqrtFunction extends OneArgumentFunction("sqrt")

case object SqrtnFunction extends FixedArgumentsFunction("sqrt", 2) {
  def getArgument(arguments: List[ExpressionNode]):ExpressionNode = arguments(1)
  def getDegree(arguments: List[ExpressionNode]):ExpressionNode = arguments(0)
}
case object FracFunction extends FixedArgumentsFunction("frac", 2) {
  def getNominator(arguments: List[ExpressionNode]):ExpressionNode = arguments(0)
  def getDenominator(arguments: List[ExpressionNode]):ExpressionNode = arguments(1)
}

case object SinFunction extends OneArgumentFunction("sin")
case object CosFunction extends OneArgumentFunction("cos")
case object TanFunction extends OneArgumentFunction("tan")
case object CotFunction extends OneArgumentFunction("cot")
case object ArcSinFunction extends OneArgumentFunction("arcsin")
case object ArcCosFunction extends OneArgumentFunction("arccos")
case object ArcTanFunction extends OneArgumentFunction("arctan")
case object ArcCotFunction extends OneArgumentFunction("arccot")