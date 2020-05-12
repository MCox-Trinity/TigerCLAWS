package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html.p
import slinky.core.StatelessComponent
import slinky.web.html._



@react class SearchForSections_SearchResultComponent extends StatelessComponent{
    case class Props(courses: Seq[shared.Course], addToActiveSchedule: (shared.Course) => Unit)


    def render(): ReactElement = {
        props.courses.zipWithIndex.map { case (c, i) => 
            // p(c.deparment, c.course_number, c.course_name, c.section, c.time, c.location, c.professor, c.day_of_week)
            div(className:="searchItem")(
                SectionSearchResultItem(c, props.addToActiveSchedule)
            )
        }
    }
}