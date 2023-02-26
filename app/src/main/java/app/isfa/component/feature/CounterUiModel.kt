package app.isfa.component.feature

data class CounterUiModel(var value: Int) {
    fun increment() = ++value
    fun decrement() = --value
}
