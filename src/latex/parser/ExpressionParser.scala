package latex.parser

import util.parsing.combinator.JavaTokenParsers
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.VarNode

class ExpressionParser extends JavaTokenParsers {
  def expr : Parser[ExpressionNode] = binOp | variable

  def variable: Parser[ExpressionNode] = """[a-z]*""".r ^^ {
    _ match {
      case s => new VarNode(s)
    }
  }

  def binOp: Parser[ExpressionNode] = variable ~ """[+*/-]""".r ~ variable ^^ {
    _ match {
      case l ~ "+" ~ r => new BinOpNode(l, r, Plus)
      case l ~ "-" ~ r => new BinOpNode(l, r, Minus)
      case l ~ "*" ~ r => new BinOpNode(l, r, Multiplication)
      case l ~ "/" ~ r => new BinOpNode(l, r, Division)
    }
  }

  def parse(s: String):ExpressionNode = {
    val result = parseAll(expr, s)
    if (!result.successful) {
      throw new RuntimeException("Could not parse:\n" + s)
    }
    result.get
  }

}

