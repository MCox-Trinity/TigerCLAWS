package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.core.StatelessComponent
import org.scalajs.dom.document
import org.scalajs.dom.html
import slinky.web.html._
import slinky.core.facade.Fragment

@react class ScheduleComponent extends StatelessComponent {
  case class Props(schedules: Map[String,Seq[shared.Course]], removeFromActiveSchedule: (shared.Course) => Unit, setActiveSchedule: (String) => Unit, createNewSchedule:(String) => Unit, activeSchedule:String)

  val plusIcon = document.getElementById("plusIcon").asInstanceOf[html.Input].value

  def createSchedule(){
    val name = "Schedule " + (props.schedules.size+1)
    props.createNewSchedule(name)
    props.setActiveSchedule(name)
  }

  def render(): ReactElement = {
    div(id:="schedulePlanner")(
      div(id:="header")(
        h1("Schedule Planner"),
        p("Use this component to plan out multiple course schedules for your semester. Clicking the ", img(src:=plusIcon), " on a course on the left screen will add it to the open schedule."),
        div(id:="buttonContainer")(button(onClick:=(_ => createSchedule()),  className:="btn btn-primary")("Create New Schedule"))
      ),
      schedulesList(),
    )
  }
  def schedulesList():ReactElement = {
    if(props.schedules == Map.empty){
      p(className:="instructions")("Click \"Create New Schedule\" to get started!")
    }
    else{
      props.schedules.zipWithIndex.map { case ((id, courses), idx) =>
        Fragment(key := idx.toString())(singleSchedule(id, courses))
      }
    }
  }

  def singleSchedule(id:String, courses:Seq[shared.Course]): ReactElement = {
    val hours = courses.map(_.course_number.substring(1,2).toInt).sum
    if(props.activeSchedule == id){
      //this is the active schedule
      div()(
        scheduleHeader(id, hours),
        div(className:="activeSched")(
          singleScheduleList(courses)
        )
      )
    }
    else{
      scheduleHeader(id, hours)
    }
  }

  def singleScheduleList(courses:Seq[shared.Course]): ReactElement = {
    if(courses.isEmpty){
      p("Add to this schedule")
    }
    else{
      courses.zipWithIndex.map{case (c, y) =>
        ScheduleItemComponent(c, props.removeFromActiveSchedule)
      }
    }
  }

  def scheduleHeader(id:String, hours:Int): ReactElement = {
    div(className:="scheduleHeader", onClick := (_ => props.setActiveSchedule(id)))(
      h1(s"${id} - ${hours} hours")
    )
  }
}