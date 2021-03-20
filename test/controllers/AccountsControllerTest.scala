package controllers

import db.tables.Account
import helpers.Utilities
import org.mockito.Mockito
import org.scalatest.FunSuite
import org.scalatestplus.play.PlaySpec
import play.api.test.{FakeRequest, Helpers}
import services.AccountService
import play.api.test._
import play.api.test.Helpers._

class AccountsControllerTest extends PlaySpec {
  val accountService:AccountService =  new AccountService()
  "Accounts Controller" should {
    "list accounts" in {

      val controller =new AccountsController(Helpers.stubControllerComponents(),accountService)
      val response = controller.list().apply(FakeRequest(Helpers.GET, "/accounts"))

      val bodyText: String = contentAsString(response)
      val expectedResult:List[Account] = Utilities.fromJson[List[Account]](bodyText)

      status(response) mustBe OK
      expectedResult(0).accNumber mustBe "12345"
      expectedResult(0).accName   mustBe "Muyinda Rogers"
      expectedResult(0).accBalance   mustBe  None
    }
  }
}
