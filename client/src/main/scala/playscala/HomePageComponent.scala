package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class HomePageComponent extends Component {
    case class Props(doLogout: () => Unit, doSearchForSections: () => Unit)
    case class State(schedules: Seq[String], username: String)

    val logoutRoute = document.getElementById("logoutRoute").asInstanceOf[html.Input].value
    implicit val ec = scala.concurrent.ExecutionContext.global


    def initialState: State = State(Nil, "{username not available yet}")

    def render(): ReactElement = {
        div (
            div (className:="Header") (
                h1 ("Home Page"),
                h2 ("Welcome " + this.state.username + "!"),
                p ("This is TigerCLAWS, an improved version of Trinity University's TigerPAWS.")
            ),
            div (className:="nav") (
                // button ("Grades (Disabled)"),
                // button ("View Degree Progress (Disabled)"),
                // button ("Current Schedule (Disabled)"),
                button ("Search/Register For Sections", id:="button-search-for-sections", onClick := (e => props.doSearchForSections())),
                button ("Logout", id:="button-login", onClick := (e => logout()))
            )
        )
    }

    def logout(): Unit = {
        FetchJson.fetchGet(logoutRoute, (bool: Boolean) => {
            props.doLogout()
        }, e => {
            println("Fetch error: " + e)
         })
    }
}