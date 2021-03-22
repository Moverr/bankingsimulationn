package implicits

import db.tables.Account
import play.api.libs.functional.syntax.{unlift,_}
import play.api.libs.json.{JsPath, Writes}



object AccountResponse {

  implicit val accountResponseWrites: Writes[Account] = (
    (JsPath \ "id").write[Long] and
    (JsPath \ "accName").write[String] and
      (JsPath \ "accNumber").write[String] and
      (JsPath \ "accBalance").write[Option[Float]]
    )(unlift(Account.unapply))


}
