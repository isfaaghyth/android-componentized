package app.isfa.component.feature

import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import app.isfa.component.R
import app.isfa.component.lib.UiComponent
import app.isfa.component.bus.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch

class CounterUiComponent constructor(
    parent: ViewGroup,
    private val eventBus: EventBus,
    private val scope: CoroutineScope
) : UiComponent(parent, R.id.uc_counter_sample), CoroutineScope by scope {

    private val txtNumber = findViewById<TextView>(R.id.txt_number)
    private val btnIncrement = findViewById<Button>(R.id.btn_increment)
    private val btnDecrement = findViewById<Button>(R.id.btn_decrement)

    private var uiModel = CounterUiModel(0)

    init {
        launch(Dispatchers.Main.immediate) {
            eventBus
                .bind(scope)
                .subscribe()
                .filterIsInstance<CounterEvent>()
                .map { update(it) }
                .collectLatest {
                    txtNumber.text = it.toString()
                }
        }

        btnIncrement.setOnClickListener {
            eventBus.emit(CounterEvent.Increment)
        }

        btnDecrement.setOnClickListener {
            eventBus.emit(CounterEvent.Decrement)
        }
    }

    private fun update(event: CounterEvent): Int {
        return when (event) {
            is CounterEvent.Increment -> uiModel.increment()
            is CounterEvent.Decrement -> if (uiModel.value > 0) {
                uiModel.decrement()
            } else {
                uiModel.value
            }
        }
    }
}
