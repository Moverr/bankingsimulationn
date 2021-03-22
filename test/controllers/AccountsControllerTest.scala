package controllers

import daos.AccountDAO
import db.tables.Account
import helpers.Utilities
import org.mockito.Mockito
import org.scalatest.FunSuite
import org.scalatestplus.play.PlaySpec
import play.api.Mode
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{FakeRequest, Helpers}
import services.AccountService
import play.api.test._
import play.api.test.Helpers._

class AccountsControllerTest extends PlaySpec {
  lazy val appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder().in(Mode.Test)
  lazy val injector: Injector = appBuilder.injector()
  lazy val dbConfProvider: DatabaseConfigProvider = injector.instanceOf[DatabaseConfigProvider]



  val accountDao = new AccountDAO(dbConfProvider)
  val accountService:AccountService =  new AccountService(accountDao)
  "Accounts Controller" should {
    "Check Balance " in {

      val controller =new AccountsController(Helpers.stubControllerComponents(),accountService)
      val response = controller.checkBalance("12345").apply(FakeRequest(Helpers.GET, "/balance"))

      val bodyText: String = contentAsString(response)
      val expectedResult = Utilities.fromJson[Account](bodyText)
      status(response) mustBe OK
      expectedResult.accNumber mustBe "12345"
    }
  }
}
