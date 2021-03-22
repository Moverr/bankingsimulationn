package implicits

import db.tables.{Account, Transaction}
import org.joda.time.DateTime
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{JsPath, Writes}
import implicits.JodaWrites._



object TransactionResponse {

  implicit val accountResponseWrites: Writes[Transaction] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "accNumber").write[String] and
      (JsPath \ "amount").write[Float] and
      (JsPath \ "transactionType").write[String] and
      (JsPath \ "transactionDate").write[String]
    )(unlift(Transaction.unapply))

}
