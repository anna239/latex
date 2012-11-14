package latex.calculator

class CalculatorException(message: String, exception: Throwable) extends Exception(message, exception) {

  def this(message: String) = this(message, null)
  def this(exception: Throwable) = this(null, exception)

}
