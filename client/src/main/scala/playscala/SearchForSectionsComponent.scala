package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html
import slinky.core.facade.ReactElement
import slinky.core.SyntheticEvent
import org.scalajs.dom.raw.Event
import models.UserData
import models.ReadsAndWrites._

@react class SearchForSectionsComponent extends Component{
    case class Props(help: () => Unit)
    case class State( fClassName: String, panelNumber: Int)

    def initialState: State = State(fClassName = "", 1)
    implicit val ec = scala.concurrent.ExecutionContext.global
    
    val csrfToken = document.getElementById("csrfToken").asInstanceOf[html.Input].value

    def render(): ReactElement = div(className:="container")(
        div(id:="search")(
            div(className:="verticalNavTabs")(
                // bc of CSS, menu items should be listed in reverse order of appearance
                p("Filters"),
                p(className := "active")("Search Results")
            ),
            
        ),
        div(id := "schedulePlanning")(
            h1("Schedules")
        )
    )
}