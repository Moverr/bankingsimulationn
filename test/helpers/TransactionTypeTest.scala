package helpers

import org.scalatestplus.play.PlaySpec

class TransactionTypeTest extends PlaySpec {

  "Transaction Type" should {
    "Return an ok response on an ok input" in {

      TransactionType.withName("debit").mustBe(TransactionType.debit)
    }


    "Throw Exception " in {

      intercept[NoSuchElementException] {   TransactionType.withName("NOTEXISST") }

    }

  }

}
