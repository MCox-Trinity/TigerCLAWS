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
    val tigerLogoRoute = document.getElementById("tigerLogo").asInstanceOf[html.Input].value

    def initialState: State = State(Nil, "TigerClaws User")

    def render(): ReactElement = {
        div(id:="homePage") (
            div(id:="left")(
                img(src:=tigerLogoRoute),
                h1(s"Welcome Back, ${this.state.username}!")
            ),
            div(id:="right")(
                section(
                    h1("My Account"),
                    div(className:="options")(
                        a("Tigerbucks Online Deposit"),
                        a("My Temporary T-Mail Password"),
                        a("Emergency Information"),
                        a("Self Service Banking Info"),
                        a("User Profile"),
                    )
                ),
                section(
                    h1("Registration"),
                    div(className:="options")(
                        a("Class Schedule/Most Current"),
                        a("Courses of Study Bulletin"),
                        a(className:="working", onClick := (_ => props.setPage("SearchForSections")))("Search for Sections"),
                        a("Express Registration"),
                        a("Search/Register for Sections"),
                        a("Add and Drop Classes"),
                        a("Manage My Waitlist"),
                        a("Registration Status"),
                        a("My class schedule"),
                        a("TrinALERT Emergency System")
                    )
                ),
                section(
                    h1("Financial Information"),
                    div(className:="options")(
                        a("Student Account Suite"),
                        a("Student Tax Information"),
                    )
                ),
                section(
                    h1("Communication"),
                    div(className:="options")(
                        a("My Documents"),
                    )
                ),
                section(
                    h1("Academic Profile"),
                    div(className:="options")(
                        a("Grades"),
                        a("Grade Point Average by Term"),
                        a("Transcript"),
                        a("Degree Audit - Common Curriculum: Catalog 2014-2015 and previous"),
                        a("Degree Audit - Pathways: Catalog 2015-16 and later"),
                        a("Test Summary"),
                        a("Apply for Graduation"),
                        a("Transcript Request & Status"),
                        a("Enrollment Verification & Status"),
                        a("My class schedule"),
                        a("My profile")
                    )
                ),
            )
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