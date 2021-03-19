package helpers

import org.scalatest.FunSuite
import org.scalatestplus.play.PlaySpec

class TransactionTypeTest extends PlaySpec {

  "Transaction Type" should {
    "Return an ok response on an ok input" in {

      TransactionType.withName("debitdebit").mustBe(TransactionType.debit)
    }


    "Throw Exception " in {

      TransactionType.withName("NOTEXISST").(TransactionType.debit)
    }

  }

}
