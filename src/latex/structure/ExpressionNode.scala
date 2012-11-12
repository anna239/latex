package latex.structure

sealed trait ExpressionNode extends Node {

}

case class VarNode(name: String) extends ExpressionNode
case class BinOpNode(left: ExpressionNode, right: ExpressionNode, op: Operation) extends ExpressionNode

sealed trait LiteralNode extends ExpressionNode

case class IntLiteral(value: Int) extends LiteralNode

