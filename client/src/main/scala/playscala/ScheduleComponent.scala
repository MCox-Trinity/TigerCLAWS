package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.core.StatelessComponent
import slinky.web.html._

@react class ScheduleComponent extends StatelessComponent {
  case class Props(courses: Seq[shared.Course], removeFromActiveSchedule: (shared.Course) => Unit)

  def render(): ReactElement = {
    if(props.courses == Nil) {
      div("You don't currently have any courses added to a schedule. Try clicking the 'plus' icon next to a course!")
    } else {
      props.courses.zipWithIndex.map { case (c, i) =>
        div(className:="searchItem")(
          ScheduleItemComponent(c, props.removeFromActiveSchedule)
        )
      }
    }
  }
}