package models

import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._
import utility.Course
import slick.jdbc.JdbcProfile
import utility.FacultyInfo
import java.sql.Time
import java.text.SimpleDateFormat
import utility.CourseGroupings
import utility.CourseGroupings.CourseGroup


class courseModel(db:Database)(implicit ec:ExecutionContext){
    val pathwayMap = {
        CourseGroupings.pathwaysGroups.map{
            case CourseGroup(name, accf) => {
                (name.split("\\(")(0).trim(),accf)
            }
        }.zipWithIndex.map{
            case ((name, accf), id) => (id, pathwayCourse(id,name,accf))
        }.toMap
    }

    def addCourse(course:Course):Future[Boolean] = {
        val date = course.dayTimes(0)
        val location = course.rooms(0).building + " " + course.rooms(0).room
        val (first_name, last_name) = findProfessorFirstLastName(course.facultyData.apply(0).name)
        val dateformat = new SimpleDateFormat("hh:mm")
        val start_time = new Time(dateformat.parse(parseTime(date.startTime)).getTime())
        val end_time = new Time(dateformat.parse(parseTime(date.endTime)).getTime())
        val c = CourseRow(-1, course.title, course.codeCredits,course.acdemicLevel,course.num,course.dept,
                course.sectionCap,start_time,end_time,date.days,location,first_name,last_name,course.section,course.enrolled)
        db.run(Tables.Course += c).map(addCount => if(addCount > 0) true else false)
    }

    def parseTime(time:String):String = {
        if(time(time.length-2) == 'A'){
            time.substring(0,time.length()-2)
        }else{
            val hours_minutes = time.split(":")
            val hour = (hours_minutes(0).toInt + 12).toString()
            val minutes = hours_minutes(1).substring(0, hours_minutes(1).length()-2)
            hour + ":" + minutes
        }
    }

   def findProfessorFirstLastName(professor:String):(String,String) = { 
       val name = professor.split(" ")
       if(name.length == 4){
           (name(1),name(3))
       }else if(name.length == 2){
           (name(0),name(1))
       }else{
           if(name(1).length == 2){
               (name(0),name(2))
           }else{
               (name(1),name(2))
           } 
       }
   }
}






