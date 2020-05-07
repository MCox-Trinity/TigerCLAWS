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

  def seedUsers():Future[Unit] = {
   val inserts = DBIO.seq(Student ++= Seq(
     StudentRow(1, "placy", BCrypt.hashpw("pass",BCrypt.gensalt()), "Parker", "Emery", "Lacy", "placy@trinity.edu", false),
     StudentRow(2, "tbutler", BCrypt.hashpw("pass",BCrypt.gensalt()), "Tara", "Glenn", "Butler", "tbutler@trinity.edu", false),
     StudentRow(3, "mlewis", BCrypt.hashpw("professor",BCrypt.gensalt()), "Mark", "C.", "Lewis", "mlewis@trinity.edu", false)
   ))
   db.run(inserts)
  }

  def getAllUsers(): Future[Seq[UserData]] = {
    db.run(
      (for{
        user <- Student
      } yield {
        user
      }).result
    ).map(users => users.map(user => UserData(user.username, user.password)))
  }
}