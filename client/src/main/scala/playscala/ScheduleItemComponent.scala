package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._
import org.scalajs.dom.document
import slinky.core.facade.ReactElement
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class ScheduleItemComponent extends Component {
  case class Props(course: shared.Course, removeFromActiveSchedule: (shared.Course) => Unit)
  case class State(open: Boolean)
  val minusIcon = document.getElementById("minusIcon").asInstanceOf[html.Input].value

  def initialState: State = State(open = false)

  def render(): ReactElement = {
    if(!state.open){
      div(className:="scheduleItem")(
        itemTitle(() => open())
      )
    } else {
      div(className:="scheduleItem-open")(
        itemTitle((() => close())),
        div(className:="desc")(
          p(em("Instructor: "), props.course.professor),
          p(em("Location: "), props.course.location),
          p(em("Course Description: "), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Quis risus sed vulputate odio ut enim blandit. Nibh sit amet commodo nulla facilisi nullam vehicula ipsum. Sit amet dictum sit amet justo donec enim diam. Fames ac turpis egestas integer eget aliquet nibh praesent tristique. Ipsum faucibus vitae aliquet nec ullamcorper sit amet risus nullam. Cursus turpis massa tincidunt dui. Arcu non sodales neque sodales ut etiam. Varius sit amet mattis vulputate enim nulla aliquet porttitor. Lorem ipsum dolor sit amet consectetur adipiscing elit pellentesque. Suspendisse sed nisi lacus sed viverra tellus in hac. Mi bibendum neque egestas congue.")
        ),
        
      )
    }
  }

  def itemTitle(clk:() => Unit):ReactElement = {
    div(className:="itemTitle", onClick:=(_ => clk()))(
      div(className:="info")(
        p(className:="title")(s"${props.course.deparment} ${props.course.course_number}-${props.course.section} ${props.course.course_name}"),
        p(className:="time")(s"${props.course.day_of_week}, ${props.course.time}")
      ),
      div(className:="options")(
        img(onClick := (_ => props.removeFromActiveSchedule(props.course)), src:=minusIcon)
      )      
    )
  }

  def open() {
    setState(state.copy(true))
  }

  def close() {
    setState(state.copy(false))
  }
}