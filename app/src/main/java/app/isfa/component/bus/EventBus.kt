package app.isfa.component.bus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

interface EventBus {
    fun subscribe(): Flow<Event>
    fun bind(scope: CoroutineScope): EventBus
    fun emit(event: Event)
}

internal class EventBusImpl : EventBus {

    private val state = MutableSharedFlow<Event>(
        replay = MAX_REPLAY
    )

    private lateinit var scope: CoroutineScope

    override fun subscribe(): Flow<Event> {
        if (!::scope.isInitialized) {
            throw Throwable("You've to bind the CoroutineScope first.")
        }

        return state
    }

    override fun bind(scope: CoroutineScope): EventBus {
        this.scope = scope
        return this
    }

    override fun emit(event: Event) {
        state.tryEmit(event)
    }

    companion object {
        private const val MAX_REPLAY = 50
    }
}
