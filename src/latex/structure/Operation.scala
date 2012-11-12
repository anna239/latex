package latex.structure

abstract sealed class Operation(name: String)

case object Plus extends Operation("+")
case object Minus extends Operation("-")
case object Multiplication extends Operation("*")
case object Division extends Operation("/")
