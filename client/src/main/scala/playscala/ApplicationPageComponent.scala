package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class ApplicationPageComponent extends Component {
  case class Props(doLogout: () => Unit)
  case class State(viewingPage: String)
  //Options: SearchForSections, Home
  def initialState: State = State("SearchForSections")

  def render(): ReactElement = {
      div(
          NavBarComponent(() => props.doLogout(), () => goHome()),
          getPageContents()
      )
  }

  def getPageContents(): ReactElement = {
        state.viewingPage match {
            case "Home" => HomePageComponent((id:String) => setPage(id))
            case "SearchForSections" => SearchForSectionsComponent(() => Unit)
        }
  }

  def setPage(id:String) = {
      setState(state.copy(id))
  }

  def goHome() = {
      setPage("Home")
  }
}
