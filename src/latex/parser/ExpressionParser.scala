package latex.parser

import util.parsing.combinator.JavaTokenParsers
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.VarNode

class ExpressionParser extends JavaTokenParsers {
  def expr = binary(minPrec) | term

  def term = variable | intValue

  def intValue = """[0-9]+""".r ^^ {
    _ match {
      case s => new IntLiteral(Integer.parseInt(s))
    }
  }

  def variable: Parser[ExpressionNode] = """[a-z]+""".r ^^ {
    _ match {
      case s => new VarNode(s)
    }
  }

  def binaryOp(level: Int): Parser[((ExpressionNode, ExpressionNode) => ExpressionNode)] = {
    level match {
      case 1 =>
        "+" ^^^ {
          (l: ExpressionNode, r: ExpressionNode) => new BinOpNode(l, r, Plus)
        } |
        "-" ^^^ {
          (l: ExpressionNode, r: ExpressionNode) => new BinOpNode(l, r, Minus)
        }
      case 2 =>
        "*" ^^^ {
          (l: ExpressionNode, r: ExpressionNode) => new BinOpNode(l, r, Multiplication)
        } |
        "/" ^^^ {
          (l: ExpressionNode, r: ExpressionNode) => new BinOpNode(l, r, Division)
        }
      case 3 =>
        "^" ^^^ {
          (l: ExpressionNode, r: ExpressionNode) => new BinOpNode(l, r, Power)
        }
      case _ => throw new RuntimeException("bad precedence level " + level)
    }
  }

  val minPrec = 1
  val maxPrec = 3

  def binary(level: Int): Parser[ExpressionNode] =
    if (level > maxPrec) term
    else binary(level + 1) * binaryOp(level)

  def parse(s: String): ExpressionNode = {
    val result = parseAll(expr, s)
    result.getOrElse(throw new RuntimeException("Could not parse:\n" + s))
  }
}

