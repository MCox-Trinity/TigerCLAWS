package shared

import play.api.libs.json.Json

case class UserData(username: String, password: String)
case class Pathway(id: Int, name: String)


object ReadsAndWrites {
  implicit val userDataReads = Json.reads[UserData]
  implicit val userDataWrites = Json.writes[UserData]
  implicit val pathwayReads = Json.reads[Pathway]
  implicit val pathwayWrites = Json.writes[Pathway]
}