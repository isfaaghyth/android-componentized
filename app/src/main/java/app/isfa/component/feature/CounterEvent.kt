package app.isfa.component.feature

import app.isfa.component.bus.Event

sealed class CounterEvent : Event {
    object Increment : CounterEvent()
    object Decrement : CounterEvent()
}
