package latex.structure

sealed case class UnaryOperation(name: String)

case object UnaryMinus extends UnaryOperation("-")