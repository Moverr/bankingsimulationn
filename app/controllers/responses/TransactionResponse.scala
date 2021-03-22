package controllers.responses

import helpers.TransactionType
import org.joda.time.DateTime

case class TransactionResponse(accNumber:String, amount:Float, transactionType:String, transactionDate:String)
