package db.schemas
import db.tables.Transaction
import slick.jdbc.H2Profile.api._


class TransactionTable(tag: Tag) extends Table[Transaction](tag,"transaction"){

  def accNumber = column[String]("accNumber")
  def amount = column[Float]("amount")
  def transactionType = column[String]("transactionType")
  def transactionDate = column[String]("transactionDate")

  override  def * = (accNumber,amount,transactionType,transactionDate) mapTo[Transaction]
}
