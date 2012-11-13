package latex.structure

sealed trait ExpressionNode extends Node

case class VarNode(name: String) extends ExpressionNode
case class BinOpNode(left: ExpressionNode, right: ExpressionNode, op: BinaryOperation) extends ExpressionNode
case class UnOpNode(operand: ExpressionNode, op: UnaryOperation) extends ExpressionNode
case class FunctionNode(function: Function, arguments: List[ExpressionNode]) extends ExpressionNode


sealed trait LiteralNode extends ExpressionNode

case class IntLiteralNode(value: Int) extends LiteralNode
case class DoubleLiteralNode(value: Double) extends LiteralNode

