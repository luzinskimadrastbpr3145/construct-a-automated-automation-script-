Kotlin
import kotlinx.coroutines.*
import kotlinx.html.*
import kotlinx.css.*
import react.*
import react.dom.*

data class AutomationScript(
    val id: Int,
    val name: String,
    val script: String,
    val enabled: Boolean
)

data class AutomationScriptDashboardState(
    val scripts: List<AutomationScript> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

val AutomationScriptDashboard = functionalComponent< AutomationScriptDashboardProps> { props ->
    val (state, setState) = useState(AutomationScriptDashboardState())

    useEffect Once {
        setState { state.copy(isLoading = true) }
        val scripts = listOf(
            AutomationScript(1, "Script 1", "echo 'Hello World!'", true),
            AutomationScript(2, "Script 2", "echo 'Hello again!'", false)
        )
        setState { state.copy(scripts = scripts, isLoading = false) }
    }

    div(classes = "dashboard") {
        h1 { +"Automation Script Dashboard" }
        if (state.isLoading) {
            p { +"Loading..." }
        } else {
            table {
                thead {
                    tr {
                        th { +"ID" }
                        th { +"Name" }
                        th { +"Script" }
                        th { +"Enabled" }
                    }
                }
                tbody {
                    state.scripts.forEach { script ->
                        tr {
                            td { +script.id.toString() }
                            td { +script.name }
                            td { +script.script }
                            td {
                                input(type = InputType.checkbox) {
                                    attrs {
                                        checked = script.enabled
                                        onChangeFunction = { props.onScriptToggle(script.id) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (state.error != null) {
            p { +"Error: ${state.error}" }
        }
    }
}

interface AutomationScriptDashboardProps : Props {
    var onScriptToggle: (scriptId: Int) -> Unit
}

fun main() {
    render(document.getElementById("root")) {
        child(AutomationScriptDashboard) {
            attrs.onScriptToggle = { scriptId ->
                println("Script $scriptId toggled")
            }
        }
    }
}