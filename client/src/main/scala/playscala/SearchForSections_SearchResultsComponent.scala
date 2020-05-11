package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html.p
import slinky.core.StatelessComponent



@react class SearchForSections_SearchResultComponent extends StatelessComponent{
    case class Props(courses: Seq[shared.Course])


    def render(): ReactElement = {
        props.courses.zipWithIndex.map { case (c, i) => 
            p(c.deparment, c.course_number, c.course_name, c.section, c.time, c.location, c.professor, c.day_of_week)
        }
    }
}