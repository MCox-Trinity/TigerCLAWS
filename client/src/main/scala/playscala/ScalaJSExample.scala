package playscala

import shared.SharedMessages
import org.scalajs.dom

import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

object ScalaJSExample {

  def main(args: Array[String]): Unit = {
    if(dom.document.getElementById("react-version") != null) {
      println("Call the react stuff.")
      ReactDOM.render(
        MainComponent(),
        dom.document.getElementById("react-root")
      )
    }
  }
}
