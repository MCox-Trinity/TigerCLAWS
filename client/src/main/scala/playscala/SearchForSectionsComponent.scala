package playscala

import slinky.core.annotations.react
import slinky.core.Component
import slinky.web.html._
import org.scalajs.dom.document
import org.scalajs.dom.html
import slinky.core.facade.ReactElement
import slinky.core.SyntheticEvent
import org.scalajs.dom.raw.Event
import shared._
import shared.ReadsAndWrites._

@react class SearchForSectionsComponent extends Component{
    case class Props(help: () => Unit, 
        addToActiveSchedule: (shared.Course) => Unit, 
        removeFromActiveSchedule: (shared.Course) => Unit, 
        schedules: Map[String, Seq[shared.Course]],
        setActiveSchedule: (String) => Unit, 
        createNewSchedule:(String) => Unit,
        activeSchedule:String)

    case class State(currentPanelID:String, courses: Seq[shared.Course])

    //panel options are searchResults and filterResults
    def initialState: State = State(currentPanelID = "filterResults", Nil)

    def render(): ReactElement = div(id:="searchForSectionsPage")(
        div(id:="searchAndFilter")(
            div(id:="tabContainer")(searchAndFilterTabs()),
            div(id:="tabContents")(
                searchAndFilterContent(),
            ),
        ),
        div(id := "schedulePlanning")(
            ScheduleComponent(props.schedules, props.removeFromActiveSchedule, props.setActiveSchedule, props.createNewSchedule, props.activeSchedule)
        )
    )

    def searchResults(courses: Seq[shared.Course]):Unit = {
        setState(state.copy(courses = courses))
    }

    def searchAndFilterContent():ReactElement = 
    if(state.currentPanelID == "searchResults"){
        div(id:="searchResults")(
            SearchForSections_SearchResultComponent(state.courses, props.addToActiveSchedule)
        )
    } else {
        div(id:="filterResults")(
            SearchForSections_FilterResultsComponent(() => Unit, searchResults)
        )
    }

    def searchAndFilterTabs():ReactElement = 
        if(state.currentPanelID == "searchResults"){
            div(className:="verticalNavTabs")(
                // bc of CSS, menu items should be listed in reverse order of appearance
                p(className := "active")("Search Results"),
                p(onClick := (_ => {
                    setState(state.copy(currentPanelID = "filterResults"))
                }))("Filters"),
            )
        } else {
            div(className:="verticalNavTabs")(
                // bc of CSS, menu items should be listed in reverse order of appearance
                p(onClick := (_ => {
                    setState(state.copy(currentPanelID = "searchResults"))
                }))("Search Results"),
                p(className := "active")("Filters"),
            )
        }
}