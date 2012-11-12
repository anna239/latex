package latex.structure

sealed trait ExpressionNode extends Node {

}

case class VarNode(name: String) extends ExpressionNode
case class BinOpNode(left: ExpressionNode, right: ExpressionNode, op: Operation) extends ExpressionNode

