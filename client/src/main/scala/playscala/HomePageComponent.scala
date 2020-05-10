package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html.button
import slinky.web.html.onClick
import slinky.web.html.div
import slinky.web.html.id
import slinky.web.html.h2
import slinky.web.html.h1
import slinky.web.html.className
import slinky.web.html.h3
import slinky.web.html.p

@react class HomePageComponent extends Component {
    case class Props(doLogout: () => Unit, doSearchForSections: () => Unit)
    case class State(schedules: Seq[String], username: String)

    def initialState: State = State(Nil, "{username not available yet}")

    def render(): ReactElement = {
        div (
            div (className:="Header") (
                h1 ("Home Page"),
                h2 ("Welcome " + this.state.username + "!"),
                p ("This is TigerCLAWS, an improved version of Trinity University's TigerPAWS.")
            ),
            div (className:="Main-Nav") (
                button ("Grades (Disabled)"),
                button ("View Degree Progress (Disabled)"),
                button ("Current Schedule (Disabled)"),
                button ("Search/Register For Sections", id:="button-search-for-sections", onClick := (e => props.doSearchForSections())),
                button ("Logout", id:="button-login", onClick := (e => props.doLogout()))
            )
        )
    }
}