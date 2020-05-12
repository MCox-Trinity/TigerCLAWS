package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html

@react class ApplicationPageComponent extends Component {
  case class Props(doLogout: () => Unit)
  case class State(loggedIn: Boolean, viewingPage: String, schedules:Map[String, Seq[shared.Course]])

  var activeSchedule = ""

  val logoutRoute = document.getElementById("logoutRoute").asInstanceOf[html.Input].value
    implicit val ec = scala.concurrent.ExecutionContext.global

  //Options: SearchForSections, Home
  def initialState: State = State(true,"SearchForSections", Map.empty[String, Seq[shared.Course]])

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
            case "SearchForSections" => SearchForSectionsComponent(() => Unit, 
                                                            (course: shared.Course) => addToActiveSchedule(course), 
                                                            (course: shared.Course) => removeFromActiveSchedule(course),
                                                            state.schedules,
                                                            (id:String) => setActiveScheduleTo(id),
                                                            (id:String) => createNewSchedule(id),
                                                            activeSchedule)
        }
  }

  def setActiveScheduleTo(id: String){
      activeSchedule = id
  }

  private def createSchedule(id:String){
      val updated = state.schedules+(id -> Seq.empty)
      setState(state.copy(schedules = updated))
  }

  def createNewSchedule(id:String){
      createSchedule(id)
  }
  
  def removeFromActiveSchedule(course: shared.Course) {
      setState(state.copy(schedules = state.schedules.updated(activeSchedule, state.schedules(activeSchedule).filter(c => c != course))))
  }

  private def activeScheduleContainsCourse(course: shared.Course): Boolean = {
      state.schedules(activeSchedule).contains(course)
  }

  def addToActiveSchedule(course: shared.Course) {
      if(!activeScheduleContainsCourse(course)){
          setState(state.copy(schedules = state.schedules.updated(activeSchedule, state.schedules(activeSchedule):+course)))
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
