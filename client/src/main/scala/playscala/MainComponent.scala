package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class MainComponent extends Component {
  type Props = Unit
  case class State(loggedIn: Boolean, viewingPage: String)
  
  // def initialState: State = State(false, "")
  def initialState: State = State(false, "SearchForSections")

  def render(): ReactElement = {
    if (state.loggedIn) {
       //div ("logged in")
      //MenuComponent(() => setState(state.copy(loggedIn = false)))
      if(state.viewingPage == "SearchForSections"){
        SearchForSectionsComponent(() => Unit)
      }
      else{
        div ("logged in")
      }
    } else {
      LoginComponent(() => setState(state.copy(loggedIn = true)))
    }
  }
}
