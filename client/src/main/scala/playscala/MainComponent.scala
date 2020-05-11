package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class MainComponent extends Component {
  type Props = Unit
  case class State(loggedIn: Boolean, viewingPage: String)
  
  def initialState: State = State(false, "")
  //ef initialState: State = State(true, "SearchForSections")
  // def initialState: State = State(true, "")

  def render(): ReactElement = {
    if (state.loggedIn) {
      if(state.viewingPage == "SearchForSections"){
        SearchForSectionsComponent(() => Unit)
      }
      else{
        HomePageComponent(() => setState(state.copy(loggedIn = false)), () => setState(state.copy(viewingPage = "SearchForSections")))
      }
    } else {
      LoginComponent(() => setState(state.copy(loggedIn = true)))
    }
  }
}
