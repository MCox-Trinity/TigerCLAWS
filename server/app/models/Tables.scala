package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Course.schema ++ Student.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Course
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(varchar), Length(100,true)
   *  @param creditHour Database column credit_hour SqlType(int4)
   *  @param academicLevel Database column academic_level SqlType(int4)
   *  @param courseNumber Database column course_number SqlType(bpchar), Length(4,false)
   *  @param department Database column department SqlType(bpchar), Length(4,false)
   *  @param capacity Database column capacity SqlType(int4)
   *  @param startTime Database column start_time SqlType(time without time zone)
   *  @param endTime Database column end_time SqlType(time without time zone)
   *  @param dayOfWeek Database column day_of_week SqlType(varchar), Length(20,true)
   *  @param location Database column location SqlType(varchar), Length(20,true)
   *  @param profFirst Database column prof_first SqlType(varchar), Length(20,true)
   *  @param profLast Database column prof_last SqlType(varchar), Length(20,true)
   *  @param section Database column section SqlType(varchar), Length(2,true)
   *  @param enrolled Database column enrolled SqlType(int4) */
  case class CourseRow(id: Int, title: String, creditHour: Int, academicLevel: Int, courseNumber: String, department: String, capacity: Int, startTime: java.sql.Time, endTime: java.sql.Time, dayOfWeek: String, location: String, profFirst: String, profLast: String, section: String, enrolled: Int)
  /** GetResult implicit for fetching CourseRow objects using plain SQL queries */
  implicit def GetResultCourseRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Time]): GR[CourseRow] = GR{
    prs => import prs._
    CourseRow.tupled((<<[Int], <<[String], <<[Int], <<[Int], <<[String], <<[String], <<[Int], <<[java.sql.Time], <<[java.sql.Time], <<[String], <<[String], <<[String], <<[String], <<[String], <<[Int]))
  }
  /** Table description of table course. Objects of this class serve as prototypes for rows in queries. */
  class Course(_tableTag: Tag) extends profile.api.Table[CourseRow](_tableTag, "course") {
    def * = (id, title, creditHour, academicLevel, courseNumber, department, capacity, startTime, endTime, dayOfWeek, location, profFirst, profLast, section, enrolled) <> (CourseRow.tupled, CourseRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(title), Rep.Some(creditHour), Rep.Some(academicLevel), Rep.Some(courseNumber), Rep.Some(department), Rep.Some(capacity), Rep.Some(startTime), Rep.Some(endTime), Rep.Some(dayOfWeek), Rep.Some(location), Rep.Some(profFirst), Rep.Some(profLast), Rep.Some(section), Rep.Some(enrolled))).shaped.<>({r=>import r._; _1.map(_=> CourseRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get, _10.get, _11.get, _12.get, _13.get, _14.get, _15.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(varchar), Length(100,true) */
    val title: Rep[String] = column[String]("title", O.Length(100,varying=true))
    /** Database column credit_hour SqlType(int4) */
    val creditHour: Rep[Int] = column[Int]("credit_hour")
    /** Database column academic_level SqlType(int4) */
    val academicLevel: Rep[Int] = column[Int]("academic_level")
    /** Database column course_number SqlType(bpchar), Length(4,false) */
    val courseNumber: Rep[String] = column[String]("course_number", O.Length(4,varying=false))
    /** Database column department SqlType(bpchar), Length(4,false) */
    val department: Rep[String] = column[String]("department", O.Length(4,varying=false))
    /** Database column capacity SqlType(int4) */
    val capacity: Rep[Int] = column[Int]("capacity")
    /** Database column start_time SqlType(time without time zone) */
    val startTime: Rep[java.sql.Time] = column[java.sql.Time]("start_time")
    /** Database column end_time SqlType(time without time zone) */
    val endTime: Rep[java.sql.Time] = column[java.sql.Time]("end_time")
    /** Database column day_of_week SqlType(varchar), Length(20,true) */
    val dayOfWeek: Rep[String] = column[String]("day_of_week", O.Length(20,varying=true))
    /** Database column location SqlType(varchar), Length(20,true) */
    val location: Rep[String] = column[String]("location", O.Length(20,varying=true))
    /** Database column prof_first SqlType(varchar), Length(20,true) */
    val profFirst: Rep[String] = column[String]("prof_first", O.Length(20,varying=true))
    /** Database column prof_last SqlType(varchar), Length(20,true) */
    val profLast: Rep[String] = column[String]("prof_last", O.Length(20,varying=true))
    /** Database column section SqlType(varchar), Length(2,true) */
    val section: Rep[String] = column[String]("section", O.Length(2,varying=true))
    /** Database column enrolled SqlType(int4) */
    val enrolled: Rep[Int] = column[Int]("enrolled")
  }
  /** Collection-like TableQuery object for table Course */
  lazy val Course = new TableQuery(tag => new Course(tag))

  /** Entity class storing rows of table Student
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param username Database column username SqlType(varchar), Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true)
   *  @param firstName Database column first_name SqlType(varchar), Length(20,true)
   *  @param middleName Database column middle_name SqlType(varchar), Length(20,true)
   *  @param lastName Database column last_name SqlType(varchar), Length(20,true)
   *  @param email Database column email SqlType(varchar), Length(20,true)
   *  @param deleted Database column deleted SqlType(bool) */
  case class StudentRow(id: Int, username: String, password: String, firstName: String, middleName: String, lastName: String, email: String, deleted: Boolean)
  /** GetResult implicit for fetching StudentRow objects using plain SQL queries */
  implicit def GetResultStudentRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean]): GR[StudentRow] = GR{
    prs => import prs._
    StudentRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[String], <<[Boolean]))
  }
  /** Table description of table student. Objects of this class serve as prototypes for rows in queries. */
  class Student(_tableTag: Tag) extends profile.api.Table[StudentRow](_tableTag, "student") {
    def * = (id, username, password, firstName, middleName, lastName, email, deleted) <> (StudentRow.tupled, StudentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password), Rep.Some(firstName), Rep.Some(middleName), Rep.Some(lastName), Rep.Some(email), Rep.Some(deleted))).shaped.<>({r=>import r._; _1.map(_=> StudentRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(varchar), Length(20,true) */
    val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
    /** Database column first_name SqlType(varchar), Length(20,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(20,varying=true))
    /** Database column middle_name SqlType(varchar), Length(20,true) */
    val middleName: Rep[String] = column[String]("middle_name", O.Length(20,varying=true))
    /** Database column last_name SqlType(varchar), Length(20,true) */
    val lastName: Rep[String] = column[String]("last_name", O.Length(20,varying=true))
    /** Database column email SqlType(varchar), Length(20,true) */
    val email: Rep[String] = column[String]("email", O.Length(20,varying=true))
    /** Database column deleted SqlType(bool) */
    val deleted: Rep[Boolean] = column[Boolean]("deleted")
  }
  /** Collection-like TableQuery object for table Student */
  lazy val Student = new TableQuery(tag => new Student(tag))
}
