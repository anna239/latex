package latex.parser

import util.parsing.combinator.JavaTokenParsers
import latex.structure._
import latex.structure.BinOpNode
import latex.structure.VarNode

class ExpressionParser extends JavaTokenParsers {

  def expr = binary(minPrec) | term

  def singleTerm = variable | doubleValue | intValue | parensExpr

  def term = singleTerm | sum | mathFunction | texFunction

  def parensExpr: Parser[ExpressionNode] = "(" ~> expr <~ ")" | "[" ~> expr <~ "]"

  def sumDeclaration: Parser[(VarNode, ExpressionNode, ExpressionNode)] = "\\sum_{" ~> (((variable~"="~expr) <~ "}^") ~ arg) ^^ {
    _ match {
      case s => ((s._1._1._1, s._1._2, s._2))
    }
  }


  def sum: Parser[ExpressionNode] = sumDeclaration ~ expr ^^ {
    _ match {
      case s => new SumNode(s._1._1, s._1._2, s._1._3, s._2)
    }
  }

  def intValue = wholeNumber ^^ {
    _ match {
      case s => new IntLiteralNode(Integer.parseInt(s))
    }
  }

  def alwaysFails: Parser[String] = """\\0""".r

  def texOperator:Parser[String] = "\\" ~> Function.predefinedFunctions.map(it => it.name).foldLeft(alwaysFails)((seed, it) => seed | it)
  def mathOperator:Parser[String] = "\\" ~> Function.mathFunctions.map(it => it.name).foldLeft(alwaysFails)((seed:Parser[String], it:String) => seed | it)

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

  def arg = "{" ~> expr <~ "}" | singleTerm

  def texFunction: Parser[FunctionNode] = texOperator ~ rep1(arg) ^^ {
    _ match {
      case s => new FunctionNode(Function.forName(s._1),  s._2)
    }
  }

  def mathFunction: Parser[ExpressionNode] = mathOperator ~ "^" ~ singleTerm ~ rep1(arg) ^^ {
    _ match {
      case s => new BinOpNode(new FunctionNode(Function.forName(s._1._1._1), s._2), s._1._2, Power)
    }
  }

  def greekLetterName: Parser[String] = {
    val varLetters = Vector("epsilon", "theta", "kappa", "pi", "rho", "sigma", "phi")
    val smallLetters = Vector("alpha", "beta", "gamma", "delta", "zeta", "eta",
      "iota", "mu", "nu", "xi", "omicron", "tau", "upsilon", "chi", "psi", "omega")

    val allSmallLetters = varLetters ++ smallLetters
    val capitalizedLetters: Vector[String] = allSmallLetters.map(s => s.capitalize)
    val allLetters = (varLetters.map(s => "var" + s) ++ allSmallLetters ++ capitalizedLetters)
    allLetters.foldLeft(alwaysFails)((l, r) => l | r)
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

  def variable: Parser[VarNode] = ident ^^ {
    _ match {
      case s => new VarNode(s)
    }
  } | greekLetter

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

