package utility

import java.io.File

import scala.collection.mutable



object CourseData {
  // TODO: Figure out how to get rid of faculty who uniformly have no name and better pick faculty names.
  val semesters = readSemesters().sortBy(_._1.doubleValue)
  val semestersMap = semesters.map(t => t._1.semester -> t._2).toMap
  val departments = semesters.flatMap { case (sem, courses) => courses.map(_.dept).distinct }.distinct
  val semesterX = semesters.map(_._1.doubleValue + 2000)
  val faculty = semesters.flatMap { case (sem, courses) => courses.flatMap(c => c.facultyData.map(f => f.id -> f.name)).filter(_._2.nonEmpty) }.toMap
  
  private def readSemesters(): Array[(Semester, Array[Course])] = {
    val files = new File("data").listFiles()
    val fregex = """[FS]\d\d\.txt""".r
    for (file <- files; if fregex.findFirstIn(file.getName()).nonEmpty) yield {
      val source = scala.io.Source.fromFile(file)
      val lines = source.getLines
      var courses = List[Course]()
      var newCourse = Course.fromStrings(lines)
      while (lines.nonEmpty && newCourse.nonEmpty) {
        courses ::= newCourse.get
        newCourse = Course.fromStrings(lines)
      }
      source.close()
      Semester(file.getName.dropRight(4)) -> courses.filter(c => c.facultyData.nonEmpty).reverse.toArray
    }
  }

}