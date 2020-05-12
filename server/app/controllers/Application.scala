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
import scala.concurrent.Await
import scala.concurrent.duration._
import shared._
import shared.ReadsAndWrites._



@Singleton
class Application @Inject() (protected val dbConfigProvider: DatabaseConfigProvider, cc: MessagesControllerComponents)
  (implicit ec: ExecutionContext) extends MessagesAbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile]{

  val model = new studentModel(db)

  def withJsonBody[A](f: A => Future[Result])(implicit request: Request[AnyContent], reads: Reads[A]):Future[Result] = {
    request.body.asJson.map { body =>
      Json.fromJson[A](body) match { 
        case JsSuccess(a, path) => f(a)
        case e @ JsError(_) => Future.successful(Ok("Failed on JSON error"))//Redirect(routes.Application.login()))
      }
    }.getOrElse(Future.successful(Ok("Failed on getting request")))//Redirect(routes.Application.login())))
  }

  val course_dataset = new models.courseModel(db)

  def load = Action { implicit request =>
    Ok(views.html.reactPage())
  }
  // def login = Action{ implicit request =>
  //   Ok(views.html.loginReact())
  // }

  // def searchForSections = Action{ implicit request =>
  //   Ok(views.html.searchForSections())
  // }

  def logout = Action{ implicit request =>
    Ok(Json.toJson(true)).withSession(request.session - "username")
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
    withJsonBody[FilterRequirement]{ fr =>
      course_dataset.filterCourse(fr).map{ courses =>
        Ok(Json.toJson(courses))
      }
    }
  }

  def getAllDepartment = Action.async{ implicit request =>
    course_dataset.getAllDepartment().map{ ds =>
      Ok(Json.toJson(ds))
    }
  }

  def getAllPathway = Action { implicit request =>
    Ok(Json.toJson(course_dataset.getAllPathway()))
  }

  def getUsername = Action { implicit request => 
    {
      val username = request.session.get("username");
      username match {
        case Some(value) => {
          Ok(Json.toJson(value))
        }
        case None => {
          Ok(Json.toJson(""))
        }
      }
    }
  }



  def addCourse = Action.async { implicit request =>
    val lines = scala.io.Source.fromFile("./server/app/utility/CSAR_Raw.txt").getLines()
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

  def seedUsers = Action.async { implicit request =>
    //model.getAllUsers().map(users => Ok(Json.toJson(users)))
    Await.result(model.seedUsers(), 2.seconds)
    Future.successful(Redirect(routes.Application.load()))
  }
}
