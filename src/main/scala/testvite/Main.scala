package testvite

import scala.scalajs.js
import scala.scalajs.js.JSConverters.*
import org.scalajs.dom
import slinky.core.*
import slinky.core.facade.Hooks.{useEffect, useLayoutEffect, useRef}
import slinky.web.ReactDOM
import slinky.web.html.*

object Main {
  def main(args: Array[String]): Unit = {
    ReactDOM.render(
      MainView(()),
      dom.document.getElementById("app")
    )
  }

  val MainView = FunctionalComponent[Unit] { _ =>
    val divRef = useRef[dom.html.Div](null)

    useLayoutEffect(() => {
      // This logs an InputEvent, showing that beforeinput produces an InputEvent, not a TextEvent.
      divRef.current.addEventListener("beforeinput", event => dom.console.log("addEventListener beforeinput", event))
    }, Seq())

    div(
      contentEditable := true,
      style := js.Dynamic.literal("border" -> "solid"),
      ref := divRef,
      onInput := (e => dom.console.log("slinky sugared onInput", e.nativeEvent)),
      new slinky.core.AttrPair[_onInput_attr.type]("onInput", (e: slinky.core.SyntheticEvent[Nothing, org.scalajs.dom.Event]) =>
        // This logs an InputEvent, showing that adding event handlers this way doesn't transform the event.
        dom.console.log("slinky attrpair onInput", e.nativeEvent)
      ),
      new slinky.core.AttrPair[_onInput_attr.type]("onBeforeInput", (e: slinky.core.SyntheticEvent[Nothing, org.scalajs.dom.Event]) =>
        // This logs a TextEvent, which I don't understand.
        dom.console.log("slinky attrpair onBeforeInput", e.nativeEvent)
      ),
    )
  }
}
