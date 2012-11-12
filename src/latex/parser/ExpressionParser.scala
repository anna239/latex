package latex.parser

import util.parsing.combinator.JavaTokenParsers
import latex.structure.{Plus, BinOpNode, ExpressionNode, VarNode}

class ExpressionParser extends JavaTokenParsers {
  def expr : Parser[ExpressionNode] = binOp | variable

  def variable: Parser[ExpressionNode] = """[a-z]*""".r ^^ {
    _ match {
      case s => new VarNode(s)
    }
  }

  def binOp: Parser[ExpressionNode] = variable ~ "+" ~ variable ^^ {
    _ match {
      case l ~ op ~ r => new BinOpNode(l, r, new Plus)
    }
  }

  def parse(s: String):ExpressionNode = {
    parseAll(expr, s).get
  }

}

