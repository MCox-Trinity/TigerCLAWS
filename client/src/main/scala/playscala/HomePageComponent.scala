package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class HomePageComponent extends Component {
    case class Props(setPage: (String) => Unit)
    case class State(schedules: Seq[String], username: String)

    
    implicit val ec = scala.concurrent.ExecutionContext.global
    val usernameRoute = document.getElementById("usernameRoute").asInstanceOf[html.Input].value

    def initialState: State = State(Nil, "{username not available yet}")

    def render(): ReactElement = {
        div (
            div (className:="Header") (
                h1 ("Home Page"),
                h2 ("Welcome " + this.state.username + "!"),
                p ("This is TigerCLAWS, an improved version of Trinity University's TigerPAWS.")
            ),
            // div (className:="nav") (
            //     // button ("Grades (Disabled)"),
            //     // button ("View Degree Progress (Disabled)"),
            //     // button ("Current Schedule (Disabled)"),
            //     button ("Search/Register For Sections", id:="button-search-for-sections", onClick := (e => props.doSearchForSections())),
            //     button ("Logout", id:="button-login", onClick := (e => logout()))
            // )
        )
    }

    override def componentDidMount(): Unit =  {
        getUsername()
    }

    def getUsername(): Unit = {
        val csrfToken = document.getElementById("csrfToken").asInstanceOf[html.Input].value
        FetchJson.fetchPost(usernameRoute, csrfToken, "", (user: String) => {
            setState(state.copy(username = user))
        }, e => {
            println("getUsername fetch error: " + e)
        })
    }

    

    
}