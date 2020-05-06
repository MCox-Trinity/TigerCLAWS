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
    case class Props(help: () => Unit)
    case class State(currentPanelID:String)

    //panel options are searchResults and filterResults
    def initialState: State = State(currentPanelID = "filterResults")

    def render(): ReactElement = div(className:="container")(
        div(id:="searchAndFilter")(
            div(id:="tabContainer")(searchAndFilterTabs()),
            div(id:="tabContents")(
                searchAndFilterContent(),
            ),
        ),
        div(id := "schedulePlanning")(
            h1("Schedules")
        )
    )

    def searchAndFilterContent():ReactElement = 
    if(state.currentPanelID == "searchResults"){
        div(id:="searchResults")(
            h1("Search Results")
        )
    } else {
        div(id:="filterResults")(
            SearchForSections_FilterResultsComponent(() => Unit)
        )
    }

    def searchAndFilterTabs():ReactElement = 
        if(state.currentPanelID == "searchResults"){
            div(className:="verticalNavTabs")(
                // bc of CSS, menu items should be listed in reverse order of appearance
                p(onClick := (_ => {
                    setState(State("filterResults"))
                }))("Filters"),
                p(className := "active")("Search Results")
            )
        } else {
            div(className:="verticalNavTabs")(
                // bc of CSS, menu items should be listed in reverse order of appearance
                p(className := "active")("Filters"),
                p(onClick := (_ => {
                    setState(State("searchResults"))
                }))("Search Results")
            )
        }
}