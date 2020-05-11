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

class CourseNumberFilter(cf: CourseFilter, course_number:String)(implicit ec: ExecutionContext) extends CourseFilter{
    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        val previous_filter = cf.filterCourse()
        previous_filter.map( cooursesRows => cooursesRows.filter(_.courseNumber == course_number))
    }
}

class CreditHourFilter(cf: CourseFilter, credit_hour: Int)(implicit ec: ExecutionContext) extends CourseFilter{
    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        val previous_filter = cf.filterCourse()
        previous_filter.map( courseRows => courseRows.filter(_.creditHour == credit_hour))
    }
}

class SectionFilter(cf: CourseFilter, section: String)(implicit ec: ExecutionContext) extends CourseFilter {
   override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
       val previous_filter = cf.filterCourse()
       previous_filter.map(courseRows => courseRows.filter(_.section == section))
   }
}

class ProfessorLastFilter(cf: CourseFilter, last_name:String)(implicit ec: ExecutionContext) extends CourseFilter{
    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        val previous_filter = cf.filterCourse()
        previous_filter.map(courseRows =>courseRows.filter(_.profLast == last_name))
    }
}

class PathwayFilter(cf: CourseFilter, pathway: Map[Int, pathwayCourse], id: Int)(implicit ec: ExecutionContext) extends CourseFilter{
    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        val previous_filter = cf.filterCourse()
        previous_filter.map(courseRows => courseRows.filter(c => pathway(id).acceptFun((c.department, c.courseNumber))))
    }
}

class DayFilter(cf: CourseFilter, days: Seq[String])(implicit ec: ExecutionContext) extends CourseFilter{
    override def filterCourse(): Future[Seq[Tables.CourseRow]] = {
        val previous_filter = cf.filterCourse()
        previous_filter.map(courseRows => courseRows.filter(c => filterDays(c, days)))
    }

    def filterDays(c: CourseRow, days: Seq[String]): Boolean = {
        for(day <- days){
            if(!c.dayOfWeek.contains(day)) return false
        }
        true
    }
}
 
