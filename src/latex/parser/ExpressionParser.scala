package latex.parser

import util.parsing.combinator.JavaTokenParsers
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.VarNode

class ExpressionParser extends JavaTokenParsers {

  def expr = binary(minPrec) | term

  def term = variable | doubleValue | intValue | parensExpr | greekLetter | texFunction

  def parensExpr: Parser[ExpressionNode] = "(" ~> expr <~ ")" | "[" ~> expr <~ "]"

  def intValue = wholeNumber ^^ {
    _ match {
      case s => new IntLiteralNode(Integer.parseInt(s))
    }
  }

  def texOperator = "\\" ~> ident

  def oneCharVar = """[a-zA-Z]""".r ^^ {
    _ match {
      case s => new VarNode(s)
    }
  }

  def digit = """[0-9]""".r ^^ {
    _ match {
      case s => new IntLiteralNode(Integer.parseInt(s))
    }
  }

  def arg = "{" ~> expr <~ "}" | digit | oneCharVar

  def texFunction: Parser[FunctionNode] = texOperator ~ rep(arg) ^^ {
    _ match {
      case s => new FunctionNode(Function.forName(s._1),  s._2)
    }
  }

  def greekLetterName: Parser[String] = {
    val varLetters = Vector("epsilon", "theta", "kappa", "pi", "rho", "sigma", "phi")
    val smallLetters = Vector("beta", "gamma", "delta", "zeta", "eta",
      "iota", "mu", "nu", "xi", "omicron", "tau", "upsilon", "chi", "psi", "omega")

    val allSmallLetters = varLetters ++ smallLetters
    val capitalizedLetters: Vector[String] = allSmallLetters.map(s => s.capitalize)
    val allLetters = (varLetters.map(s => "var" + s) ++ allSmallLetters ++ capitalizedLetters)
    allLetters.foldLeft("alpha" | "Alpha")((l, r) => l | r)
  }

  def greekLetter: Parser[VarNode] = "\\" ~> greekLetterName ^^ {
    _ match {
      case s => new VarNode(s)
    }
  }

  def doubleValue = decimalNumber ^^ {
    _ match {
      case s => new DoubleLiteralNode(java.lang.Double.parseDouble(s))
    }
  }

  def variable: Parser[ExpressionNode] = """[a-zA-Z]+""".r ^^ {
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
    result.getOrElse(throw new RuntimeException("Could not parse:\n" + result.toString))
  }
}

