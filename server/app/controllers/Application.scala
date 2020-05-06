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
import play.api.libs.json._
import scala.concurrent.Future
import models._
import models.ReadsAndWrites._

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

  val model = new studentModel(db)

  def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]):Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match { 
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Redirect(routes.Application.login()))
      }
    }.getOrElse(Future.successful(Redirect(routes.Application.login())))
  }

  val course_dataset = new models.courseModel(db)

  def login = Action{ implicit request =>
    Ok(views.html.loginReact())
    //Ok(views.html.login(loginForm))
  }

  def searchForSections = Action{ implicit request =>
    Ok(views.html.searchForSections())
  }

  def logout = Action{ implicit request =>
    Ok(views.html.index("Logged out"))
  }
  
  def validateLogin = Action.async { implicit request =>
    withJsonBody[UserData] { ud =>
      model.validateUser(ud.username, ud.password).map { ouserId =>
        ouserId match {
          case Some(userid) =>
            Ok(Json.toJson(true))
              .withSession("username" -> ud.username, "userid" -> userid.toString, "csrfToken" -> play.filters.csrf.CSRF.getToken.get.value)
          case None =>
            Ok(Json.toJson(false))
        }
      }
    }
  }

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
