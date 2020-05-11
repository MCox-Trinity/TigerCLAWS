package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html
import slinky.core.facade.ReactElement
import slinky.core.SyntheticEvent
import org.scalajs.dom.raw.Event
import shared.UserData
import shared.ReadsAndWrites._


@react class LoginComponent extends Component {
  case class Props(doLogin: () => Unit)
  case class State(username: String, password: String, message: String)

  def initialState: State = State("", "", "")
  implicit val ec = scala.concurrent.ExecutionContext.global

  def render(): ReactElement = {
    val logoImgRoute = document.getElementById("logoImgRoute").asInstanceOf[html.Input].value
    div ( id := "loginPage")(
    div ( className := "loginMenu" )(
      div ( className := "loginMenuHeader" )(
        div ( className := "logoContainer")(
          img ( id := "trinityLogo", src := logoImgRoute)
        ),
        div ( className := "applicationName")(
          h1 ("TigerCLAWS"),
          h2 ("Login")
        )
      ),
      div ( className := "loginMenuBody", id := "loginForm")(
        "Username", 
        br(),
        input( `type` := "text", value := state.username,
          onChange :=  (e => setState(state.copy(username = e.target.value)))                       
        ),
        br(),br(),
        "Password",
        br(),
        input( `type` := "password", value := state.password,
          onChange :=  (e => setState(state.copy(password = e.target.value)))                       
        ),
        div ( className := "form-actions" )(
          state.message,
          button ("Login", onClick := (e => login())),
        )
      ),
    )
  )
}

  def login(): Unit = {
     val csrfToken = document.getElementById("csrfToken").asInstanceOf[html.Input].value
    val validateRoute = document.getElementById("validateRoute").asInstanceOf[html.Input].value
    FetchJson.fetchPost(validateRoute, csrfToken, UserData(state.username, state.password), (bool: Boolean) => {
      if(bool) {
        props.doLogin()
      } else {
        setState(state.copy(message = "Login Failed"))
      }
    }, e => {
      println("Fetch error: " + e)
    })
  }
}