package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._

@react class MainComponent extends Component {
  type Props = Unit
  case class State(loggedIn: Boolean)
  
  def initialState: State = State(false)

  def render(): ReactElement = {
    if (state.loggedIn) {
      div ("logged in")//MenuComponent(() => setState(state.copy(loggedIn = false)))
    } else {
      LoginComponent(() => setState(state.copy(loggedIn = true)))
    }
  }
}