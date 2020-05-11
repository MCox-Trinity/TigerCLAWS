package shared

import play.api.libs.json.Json

case class UserData(username: String, password: String)
case class Pathway(id: Int, name: String)
case class FilterRequirement(credit_hour: Option[Int], dept:Option[String],
          course_number: Option[String], course_name: Option[String], section: Option[String],
          professor_last: Option[String],pathwayId:Option[Int])
case class Course(deparment:String, course_number:String, course_name:String, professor:String, section:String,
                  time:String, location:String)

object ReadsAndWrites {
  implicit val userDataReads = Json.reads[UserData]
  implicit val userDataWrites = Json.writes[UserData]
  implicit val pathwayReads = Json.reads[Pathway]
  implicit val pathwayWrites = Json.writes[Pathway]
  implicit val filterRequirementReads = Json.reads[FilterRequirement]
  implicit val filterRequirementWrites = Json.writes[FilterRequirement]
  implicit val courseReads = Json.reads[shared.Course]
  implicit val courseWrites = Json.writes[shared.Course]
}