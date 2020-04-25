package utility



object CourseGroupings {
  case class CourseGroup(name: String, acceptFunc: ((String,String)) => Boolean)

  // val startSemester = Semester("F17")
  
  // val consideredCourses = CourseData.semesters.filter(_._1 >= startSemester)
  
  // val departmentGroups: Seq[CourseGroup] = {
  //   val depts = consideredCourses.flatMap(_._2.map(_.dept).distinct)
  //   depts.flatMap(dept => Seq(CourseGroup(dept + " - Lower Division", c => c.dept == dept && c.num(0) - '0' < 3),
  //                             CourseGroup(dept + " - Upper Division", c => c.dept == dept && c.num(0) - '0' >= 3)))
  // }
  
  val pathwaysGroups: Seq[CourseGroup] = {
    val source = scala.io.Source.fromFile("./server/app/utility/pathways-from-pdf.txt")
    val lines = source.getLines()
    val courseRegex = """\W*(\w{3,4}) +(\d{4}) .+""".r
    def buildGroups(lines: Iterator[String]): List[(String, Set[(String, String)])] = {
      if (lines.hasNext) {
        val name = lines.next()
        val courses = for (courseRegex(dept, num) <- lines.takeWhile(_.nonEmpty)) yield (dept, num)
        (name, courses.toSet) :: buildGroups(lines)
      } else Nil
    }
    val ret = buildGroups(lines).map { case (str, set) => CourseGroup(str, c => set.contains(c))}
    source.close()
    ret
  }

  // val courseGroups = pathwaysGroups ++ departmentGroups
}
