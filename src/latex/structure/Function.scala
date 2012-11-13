package latex.structure

/**
 * Created with IntelliJ IDEA.
 * User: anna
 * Date: 13/11/12
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
abstract sealed case class Function(name: String)

abstract sealed case class FixedArgumentsFunction(override val name: String, argumentsCount: Int) extends Function(name)

