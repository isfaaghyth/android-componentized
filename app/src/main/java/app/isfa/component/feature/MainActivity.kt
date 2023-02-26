package app.isfa.component.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import app.isfa.component.R
import app.isfa.component.lib.component
import app.isfa.component.bus.EventBus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var bus: EventBus

    private val counterComponent by component(
        isEagerComponent = true
    ) {
        CounterUiComponent(
            parent = it,
            eventBus = bus,
            scope = lifecycleScope
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
