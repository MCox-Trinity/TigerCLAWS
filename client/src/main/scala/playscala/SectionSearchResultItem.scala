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

@react class SectionSearchResultItem extends Component{
    case class Props(course: shared.Course, addToActiveSchedule: (shared.Course) => Unit)
    case class State(open:Boolean)

    def initialState: State = State(open = false)
    val plusIcon = document.getElementById("plusIcon").asInstanceOf[html.Input].value

    def render(): ReactElement = {
        if(!state.open){
            div(className:="sectionSearchResultItem")(
                itemTitle(() => open())
            )
        }
        else {
            div(className:="sectionSearchResultItem-open")(
                itemTitle((() => close())),
                p(className:="desc")(em("Course Description: "), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Quis risus sed vulputate odio ut enim blandit. Nibh sit amet commodo nulla facilisi nullam vehicula ipsum. Sit amet dictum sit amet justo donec enim diam. Fames ac turpis egestas integer eget aliquet nibh praesent tristique. Ipsum faucibus vitae aliquet nec ullamcorper sit amet risus nullam. Cursus turpis massa tincidunt dui. Arcu non sodales neque sodales ut etiam. Varius sit amet mattis vulputate enim nulla aliquet porttitor. Lorem ipsum dolor sit amet consectetur adipiscing elit pellentesque. Suspendisse sed nisi lacus sed viverra tellus in hac. Mi bibendum neque egestas congue.")
            )
        }
        
    }

    def itemTitle(clk:() => Unit):ReactElement = {
        div(className:="searchResultItemTitle", onClick:=(_ => clk()))(
            div(className:="info")(
                p(className:="med")(s"${props.course.deparment} ${props.course.course_number}-${props.course.section}"),
                p(className:="lrg")(props.course.course_name),
                p(className:="med")(props.course.professor),
                p(className:="med")(s"${props.course.day_of_week}, ${props.course.time}"),
            ),
            img(src:=plusIcon, onClick := (_ => props.addToActiveSchedule(props.course)))
        )
    }

    def open(){
        setState(state.copy(true))
    }

    def close(){
        setState(state.copy(false))
    }
}