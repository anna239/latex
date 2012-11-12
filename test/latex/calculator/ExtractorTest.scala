package latex.calculator

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import latex.formulaextractor.FormulaExtractor


class ExtractorTest extends FlatSpec with ShouldMatchers {
  val extractor = new FormulaExtractor

  extractor.extract("English authors use $tan(x)$ iinstead of $$tg(x)$$") shouldBe Array("tan(x)", "tg(x)")
  extractor.extract("Note that \\$1000000 = \\$ $$10^6$$") shouldBe Array("10^6")

  extractor.extract("It can be easily shown that equation" +
    " $$a^n+b^n=c^n$$ has no natural roots for $n>=3$") shouldBe Array("a^n+b^n=c^n", "n>=3")
}
