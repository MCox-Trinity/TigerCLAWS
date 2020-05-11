package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class NavBarComponent extends Component {
  case class Props(doLogout: () => Unit, goHome: () => Unit)
  type State = Unit
  def initialState: Unit = Unit
  
  val tigerLogoRoute = document.getElementById("tigerLogo").asInstanceOf[html.Input].value
  implicit val ec = scala.concurrent.ExecutionContext.global

  def render(): ReactElement = {
    div(id:="nav")(
      div(id:="left")(
        a(onClick := (_ => props.goHome()))("Home"),
      ),
      div(id:="center")(
        img(src:=tigerLogoRoute),
        h1("TigerCLAWS")
      ),
      div(id:="right")(
        a(onClick := (_ => props.doLogout()))("Logout")
      )
    )
  }
}