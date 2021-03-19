package controllers.requests

import helpers.TransactionType
import org.joda.time.DateTime

case class TransactionRequest(accNumber:String,amount:Float,transactionType:TransactionType.Value,dateCreated:DateTime)
