package controllers

import daos.TransactionDAO
import db.tables.Account
import org.joda.time.DateTime
import org.mockito.Mockito
import org.scalatest.FunSuite
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import services.{AccountService, TransactionService}

import scala.concurrent.Future
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import org.mockito.Mockito

class TransactionsControllerTest extends PlaySpec {

  val account =   Account("Muyinda Rogers","12345",None)
  val date_created:DateTime = DateTime.now()
  val jsonRequest = Json.parse("{\"accnumber\":\"12345\", \"amount\":\"10000\", \"date_created\":\""+date_created+"\" }")

  "TransactionsController" should{
    "credit Account" in {
      //todo: mock the account service
      val accountService:AccountService = Mockito.mock(classOf[AccountService])
      val transactionService:TransactionService = new TransactionService(accountService,new TransactionDAO())

      Mockito.when(accountService.getByAccNumber(account.accNumber)).thenReturn(Some(account))
      val controller   = new TransactionsController(Helpers.stubControllerComponents(),transactionService)
      val response = controller.credit().apply(FakeRequest(Helpers.POST, "/deposit").withJsonBody(jsonRequest))
      status(response) mustBe OK
    }

    "debit Account" in {

    }



  }
}
