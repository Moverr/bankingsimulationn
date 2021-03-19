package db.tables

import org.scalatestplus.play.PlaySpec

class AccountTest extends PlaySpec{
"Test Account Table " should  {
  val account:Account = Account("Rogers","67876",Some(23434))
 "Initialize propery as expected " in  {
    account.accName mustBe("Rogers")
    account.accNumber mustBe("67876")
    account.accBalance mustBe(Some[23434])
 }
}
}
