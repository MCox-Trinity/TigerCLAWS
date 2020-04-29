package controllers

import javax.inject._

import play.api.mvc._
import collection.mutable
import play.api.data._
import play.api.data.Forms._
import scala.concurrent.ExecutionContext
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import utility.Course
import play.api.libs.json.Json
import scala.concurrent.Future

case class LoginData(username: String, password: String)

@Singleton
class Application @Inject() (protected val dbConfigProvider: DatabaseConfigProvider, cc: MessagesControllerComponents)
  (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile]{
  val loginForm = Form(
    mapping(
      "Username" -> text(),
      "Password" -> text()
    )(LoginData.apply)(LoginData.unapply)
  )

  val course_dataset = new models.courseModel(db)

  def login = Action{ implicit request =>
    Ok(views.html.login(loginForm))
  }
  
  def validateLogin = TODO

  def filterCourse = Action.async{ implicit request =>
    course_dataset.filterCourse("CSCI").map{ ret =>
      println(ret.mkString)
      Ok("")
    }
  }

  def addCourse = Action.async { implicit request =>
    val lines = scala.io.Source.fromFile("./server/app/utility/CSAR_RAW.txt").getLines()
    var courses = List[Course]()
    while(lines.hasNext){
        Course.fromStrings(lines) match {
            case Some(c) => {
                //println(c)
                courses = c :: courses}
            case None => 
          }
    }
    courses.filter(c => c.dayTimes.length == 1 && c.facultyData.length == 1 && c.rooms.length == 1)
    .foldLeft(Future.successful(Ok(Json.toJson(true)))){
      (f, c) => {
        course_dataset.addCourse(c).map(b => Ok(Json.toJson(b)))
      }
    }
  }
}
