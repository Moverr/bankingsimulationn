package controllers

import daos.TransactionDAO
import db.tables.{Account, Transaction}
import helpers.Utilities
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


  val transactionDate:DateTime = DateTime.now()
  val jsonRequest = Json.parse("{\"accnumber\":\"12345\", \"amount\":\"1000\", \"transaction_date\":\""+transactionDate+"\" }")

  "TransactionsController" should{

    val accountService:AccountService = Mockito.mock(classOf[AccountService])
    val transactionService:TransactionService = new TransactionService(accountService,new TransactionDAO())

    "credit Account" in {
      val account =   Account(0L,"Muyinda Rogers","12345",None)
      //todo: mock the account service
      Mockito.when(accountService.getByAccNumber(account.accNumber)).thenReturn(Some(account))
      val controller   = new TransactionsController(Helpers.stubControllerComponents(),transactionService)
      val response = controller.credit().apply(FakeRequest(Helpers.POST, "/deposit").withJsonBody(jsonRequest))
      val bodyText: String = contentAsString(response)
      val expectedResult:Transaction = Utilities.fromJson[Transaction](bodyText)


      status(response) mustBe OK
      expectedResult.accNumber mustBe "12345"
      expectedResult.amount   mustBe 1000.0
      expectedResult.transactionDate   mustBe  transactionDate.toString("yyyy-MM-d")


    }

    "debit Account" in {
      val account =   Account(0L,"Muyinda Rogers","12345",Some(2000))
      //todo: mock the account service
      Mockito.when(accountService.getByAccNumber(account.accNumber)).thenReturn(Some(account))
      val controller   = new TransactionsController(Helpers.stubControllerComponents(),transactionService)
      val response = controller.debit().apply(FakeRequest(Helpers.POST, "/withdraw").withJsonBody(jsonRequest))
      val bodyText: String = contentAsString(response)
      val expectedResult:Transaction = Utilities.fromJson[Transaction](bodyText)


      status(response) mustBe OK
      expectedResult.accNumber mustBe "12345"
      expectedResult.amount   mustBe 1000.0
      expectedResult.transactionDate   mustBe  transactionDate.toString("yyyy-MM-d")

    }



  }
}
