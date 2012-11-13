package latex.structure

abstract sealed case class Function(name: String)

object Function {
  val predefinedFunctions = List(SqrtFunction, SqrtnFunction, FracFunction)

  def forName(name: String): Function = {
    predefinedFunctions.find({
      name == _.name
    }).getOrElse(throw new IllegalStateException("Unknown function: " + name))
  }
}

abstract sealed case class FixedArgumentsFunction(override val name: String, argumentsCount: Int) extends Function(name)

case object SqrtFunction extends FixedArgumentsFunction("sqrt", 1) {
  def getArgument(arguments: List[ExpressionNode]):ExpressionNode = arguments(0)
}
case object SqrtnFunction extends FixedArgumentsFunction("sqrt", 2) {
  def getArgument(arguments: List[ExpressionNode]):ExpressionNode = arguments(1)
  def getDegree(arguments: List[ExpressionNode]):ExpressionNode = arguments(0)
}
case object FracFunction extends FixedArgumentsFunction("frac", 2) {
  def getNominator(arguments: List[ExpressionNode]):ExpressionNode = arguments(0)
  def getDenominator(arguments: List[ExpressionNode]):ExpressionNode = arguments(1)
}