package implicits

import db.tables.{Account, Transaction}
import org.joda.time.DateTime
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{JsPath, Writes}
import implicits.JodaWrites._



object TransactionResponse {

  implicit val accountResponseWrites: Writes[Transaction] = (
    (JsPath \ "accNumber").write[String] and
      (JsPath \ "amount").write[Float] and
      (JsPath \ "transactionType").write[String] and
      (JsPath \ "dateCreated").write[String] and
      (JsPath \ "dateUpdated").write[String]
    )(unlift(Transaction.unapply))

}
