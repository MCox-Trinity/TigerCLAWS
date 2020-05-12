package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class ApplicationPageComponent extends Component {
  case class Props(doLogout: () => Unit)
  case class State(loggedIn: Boolean, viewingPage: String)

  val logoutRoute = document.getElementById("logoutRoute").asInstanceOf[html.Input].value
    implicit val ec = scala.concurrent.ExecutionContext.global

  //Options: SearchForSections, Home
  def initialState: State = State(true,"Home")

  def render(): ReactElement = {
      div(
          NavBarComponent(() => setState(state.copy(loggedIn = false)), () => goHome()),
          getPageContents()
      )
  }
 override def componentDidUpdate(prevProps: Props, prevState: State): Unit = {
    if (state.loggedIn == false) {
          logout()
      }
 }


  def getPageContents(): ReactElement = {
        state.viewingPage match {
            case "Home" => HomePageComponent((id:String) => setPage(id))
            case "SearchForSections" => SearchForSectionsComponent(() => Unit)
        }
  }

  def setPage(id:String) = {
      setState(state.copy(viewingPage = id))
  }

  def goHome() = {
      setPage("Home")
  }
   def logout(): Unit = {
        FetchJson.fetchGet(logoutRoute, (bool: Boolean) => {
            props.doLogout()
        }, e => {
            println("Fetch error: " + e)
         })
    }
}
