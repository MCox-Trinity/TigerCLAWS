package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt

class studentModel(db: Database)(implicit ec: ExecutionContext) {
  def validateUser(username: String, password: String): Future[Option[Int]] = {
    val matches = db.run(Student.filter(studentRow => studentRow.username === username).result)
    matches.map(studentRows => studentRows.headOption.flatMap {
      studentRow => if (BCrypt.checkpw(password, studentRow.password)) Some(studentRow.id) else None
    })
  }
}

//Make a createAllUsers route and have it do nothing on front end,
//but add seed users to database in background.