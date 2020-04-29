package models

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._
import models.Tables._
import scala.concurrent.ExecutionContext

trait CourseFilter{
    def filterCourse():Future[Seq[CourseRow]]
}

class BaseFilter(implicit db: Database) extends CourseFilter{
    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        db.run(Course.filter(courseRow => courseRow.enrolled < courseRow.capacity).result)
    }
}


class TitleFilter(cf :CourseFilter, title:String)(implicit db: Database,ec:ExecutionContext) extends CourseFilter{
    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        val previous_filter = cf.filterCourse()
        previous_filter.map( courseRows => courseRows.filter(_.title == title))
    }
}

class DeptFilter(cf: CourseFilter, department:String)(implicit db:Database, ec:ExecutionContext) extends CourseFilter{

    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        val previous_filter = cf.filterCourse()
        previous_filter.map( courseRows => courseRows.filter(_.department == department))
    }
}