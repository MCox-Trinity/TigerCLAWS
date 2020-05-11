package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html
import slinky.core.facade.ReactElement
import slinky.core.SyntheticEvent
import org.scalajs.dom.raw.Event
import shared._
import shared.ReadsAndWrites._
import slinky.web.html.tabIndex.tag
import org.scalajs.dom.raw.Element
import slinky.core.CustomAttribute

@react class SearchForSections_FilterResultsComponent extends Component {
    case class Props(help: () => Unit, pass_course: Seq[shared.Course] => Unit)
    case class State(meetingTimeOptions:Array[String], departments:Seq[String], pathways: Seq[shared.Pathway])

    def initialState: State = State(meetingTimeOptions = Array(
        "7 AM",
        "8 AM",
        "9 AM",
        "10 AM",
        "11 AM",
        "12 PM",
        "1 PM",
        "2 PM",
        "3 PM",
        "4 PM",
        "5 PM",
        "6 PM",
        "7 PM",
        "8 PM",
        "9 PM", 
        "10 PM",
        "11 PM"
    ), Nil, Nil)

    implicit val ec = scala.concurrent.ExecutionContext.global
    val departmentRoutes = document.getElementById("allDepartments").asInstanceOf[html.Input].value
    val pathwayRoutes = document.getElementById("allPathways").asInstanceOf[html.Input].value
    val searchClassRoutes = document.getElementById("filterCourse").asInstanceOf[html.Input].value
    val csrfToken = document.getElementById("csrfToken").asInstanceOf[html.Input].value
    
    override def componentDidMount(): Unit = {
        getDepartment()
        getPathway()
    }

    def getDepartment(): Unit = {
        FetchJson.fetchGet(departmentRoutes, (departments: Seq[String])=> {
            setState(state.copy(departments = departments.sorted))
        }, e => {
            println("Fetch error: " + e)
        })
    }

    def getPathway(): Unit = {
        FetchJson.fetchGet(pathwayRoutes, (pathways:Seq[shared.Pathway]) => {
            setState(state.copy(pathways = pathways))
        }, e => {
            println("Fetch error: " + e)
        })
    }

    def parseInput[A <: html.Input](id:String, attribute: String): Option[String] = {
        val value = document.getElementById(id).asInstanceOf[A].value
        if(value == "") None else Some(value)
    }

    def parsePathway(id: String): Option[Int] = {
        val selection = document.getElementById(id).asInstanceOf[html.Select]
        val selected_index = selection.selectedIndex
        val a = selection.options(selected_index).getAttribute("pathwayid")
        if(a == "-1") None  else Some(a.toInt)
    }

    def searchClass(): Unit = {
       val department = parseInput("department", "value")
       val credit_hour = parseInput("credit_hour", "value").map(_.toInt)
       val course_number = parseInput("course_number", "value")
       val course_name = parseInput("course_name", "value")
       val section = parseInput("section", "value")
       val last_name = parseInput("last_name", "value")
       val pathwayId = parsePathway("pathway")
       val requirements = FilterRequirement(credit_hour,department,course_number,course_name,section,last_name,pathwayId)
       FetchJson.fetchPost(searchClassRoutes, csrfToken,requirements, (courses: Seq[shared.Course]) => {
           props.pass_course(courses)
           //println(courses.mkString(", "))
       }, e => {
           println("Fetch error: " + e)
       })
    }

    def render(): ReactElement ={
        div(
            h1(id:="pageTitle")("Filter Search Results"),
            div(id := "filterSection")(
                h1(className:="sectionHeader")("By Meeting Time"),
                div(id:="fitsInMySched")(
                    div(
                    label(className := "checkbox")(
                        input(`type` := "checkbox"),
                        span(className := "checkmark")
                        ),
                    ),
                    div(id:="fitsInMySchedDesc")(
                        p("Fits in my Schedule"),
                        p(className:="desc")("This feature will use the schedule that you have open in the \"Schedule Planning\" tab and only show you classes that don't conflict with those that you have already added.")
                    )
                ),
                br(),
                div(id := "specificMeetingTime")(
                    h1(className:="subsectionHeader")("By Specific Meeting Time"),
                    div(id:="meetingTimeOptions")(
                        div(className:="dayOfTheWeekCheckbox")(
                            div(
                                label(className := "checkbox")(
                                    input(`type` := "checkbox"),
                                    span(className := "checkmark")
                                ),
                            ),
                            br(),
                            p("M")
                        ),
                        div(className:="dayOfTheWeekCheckbox")(
                            div(
                                label(className := "checkbox")(
                                    input(`type` := "checkbox"),
                                    span(className := "checkmark")
                                ),
                            ),
                            br(),
                            p("T")
                        ),
                        div(className:="dayOfTheWeekCheckbox")(
                            div(
                                label(className := "checkbox")(
                                    input(`type` := "checkbox"),
                                    span(className := "checkmark")
                                ),
                            ),
                            br(),
                            p("W")
                        ),
                        div(className:="dayOfTheWeekCheckbox")(
                            div(
                                label(className := "checkbox")(
                                    input(`type` := "checkbox"),
                                    span(className := "checkmark")
                                ),
                            ),
                            br(),
                            p("Th")
                        ),
                        div(className:="dayOfTheWeekCheckbox")(
                            div(
                                label(className := "checkbox")(
                                    input(`type` := "checkbox"),
                                    span(className := "checkmark")
                                ),
                            ),
                            br(),
                            p("F")
                        ),
                        div(className:="dayOfTheWeekCheckbox selectOption")(
                            div(
                                select()(
                                    option("7 AM"),
                                    option("8 AM"),
                                    option("9 AM"),
                                    option("10 AM"),
                                    option("11 AM"),
                                    option("12 PM"),
                                    option("1 PM"),
                                    option("2 PM"),
                                    option("3 PM"),
                                    option("4 PM"),
                                    option("5 PM"),
                                    option("6 PM"),
                                    option("7 PM"),
                                    option("8 PM"),
                                    option("9 PM"),
                                    option("10 PM"),
                                )
                            ),
                            p("From")
                        ),
                        div(className:="dayOfTheWeekCheckbox selectOption")(
                            div(
                                select(
                                    option("7 AM"),
                                    option("8 AM"),
                                    option("9 AM"),
                                    option("10 AM"),
                                    option("11 AM"),
                                    option("12 PM"),
                                    option("1 PM"),
                                    option("2 PM"),
                                    option("3 PM"),
                                    option("4 PM"),
                                    option("5 PM"),
                                    option("6 PM"),
                                    option("7 PM"),
                                    option("8 PM"),
                                    option("9 PM"),
                                    option("10 PM"),
                                )
                            ),
                            p("To")
                        )
                    )
                    
                ),
                br(),
                div(id:="byCourse")(
                    h1(className:="sectionHeader")("By Course"),
                    div(id:="pathwaysReqs")(
                        h1("Pathways Requirements"),
                        div(id:="pathwaysOptions")(
                            div(id:="itemLabels")(
                                p("Requirements"),
                            ),
                            div(id:="selectors")(
                                select(id := "pathway")(
                                    option(key := "0", "", new CustomAttribute[String]("pathwayid") := "-1"), 
                                    state.pathways.zipWithIndex.map { case (d, i) => 
                                        option (key := i.toString, d.name, new CustomAttribute[String]("pathwayid") := d.id.toString())
                                    }

                                ),
                            )
                        ),
                    ),
                    div(id:="courseDetails")(
                        h1(className:="subsectionHeader")("Course Details"),
                        div(id:="courseDetailsOptions")(
                            div(className:="courseDetail")(
                                p("# Credit Hours"),
                                select(id := "credit_hour")(
                                    option(""),
                                    option("0"),
                                    option("1"),
                                    option("2"),
                                    option("3"),
                                    option("4")
                                )
                            ),
                            div(className:="courseDetail")(
                                p("Subject/Department"),
                                select(id := "department")(
                                    option(key := "0", ""),
                                    state.departments.zipWithIndex.map { case (d, i) => 
                                        option (key := (i+1).toString,d)
                                    }
                                )
                            ),
                            div(className:="courseDetail")(
                                p("Course Level"),
                                select(
                                    option("Lower Division"),
                                    option("Upper Division"),
                                    option("Undergraduate"),
                                    option("Graduate")
                                )
                            ),
                            div(className:="courseDetail")(
                                p("Course Number"),
                                input(`type` := "text", id := "course_number")
                            ),
                            div(className:="courseDetail")(
                                p("Section"),
                                input(`type` := "text", id :="section")
                            ),
                            div(className:="courseDetail")(
                                p("Section Status"),
                                select(
                                    option("Open"),
                                    option("Waitlisted"),
                                    option("Closed")
                                )
                            ),
                            div(className:="courseDetail")(
                                p("Course Title Kewords"),
                                input(`type` := "text", id := "course_name")
                            ),
                            div(className:="courseDetail")(
                                p("Instructor Last Name"),
                                input(`type` := "text", id := "last_name")
                            ),
                            div(className:="courseDetail")(
                                p("Location"),
                                select(
                                    option("Campus"),
                                    option("Off-Campus"),
                                    option("Abroad")
                                )
                            ),
                        )
                    ),
                    div(id:="buttons")(
                        button(className:="btn btn-secondary")("Clear Filters"),
                        button(className:="btn btn-primary", onClick := (e => searchClass()))("Submit"),
                    )
                )
            )
        );
     }
}
