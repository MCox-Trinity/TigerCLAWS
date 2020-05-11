package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class NavBarComponent extends Component {
  case class Props(doLogout: () => Unit)
  type State = Unit
  def initialState: Unit = Unit
  
  val logoImgRoute = document.getElementById("logoImgRoute").asInstanceOf[html.Input].value
  val logoutRoute = document.getElementById("logoutRoute").asInstanceOf[html.Input].value
  implicit val ec = scala.concurrent.ExecutionContext.global

  def render(): ReactElement = {
    div (className :="page")(
      div (className :="nav")(
        div (id:="left")(
          a ("Home")
        ),
        div (id:="center")(
          img (id := "tigerLogo", src := logoImgRoute),
          h1 ("TigerCLAWS")
        ),
        div (id:="right")(
          a (id:="logoutLink", onClick := (e => logout()))("Logout")
        )
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