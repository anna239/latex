package latex.structure

abstract sealed case class BinaryOperation(name: String)

case object Plus extends BinaryOperation("+")
case object Minus extends BinaryOperation("-")
case object Multiplication extends BinaryOperation("*")
case object Division extends BinaryOperation("/")
case object Power extends BinaryOperation("^")
