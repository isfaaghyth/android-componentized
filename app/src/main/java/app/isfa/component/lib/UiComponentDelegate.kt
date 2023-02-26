package app.isfa.component.lib

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import app.isfa.component.lib.observer.ImmediateLifecycleObserver
import app.isfa.component.lib.observer.UiComponentLifecycleObserver
import app.isfa.component.lib.util.addSafeObserver
import app.isfa.component.lib.util.createUiComponent
import app.isfa.component.lib.util.rootCurrentView
import app.isfa.component.lib.util.safeLifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class UiComponentDelegate<Ui : BaseUiComponent>(
    private val lifecycleOwner: LifecycleOwner,
    private val componentCreation: (ViewGroup) -> Ui,
    isEagerComponent: Boolean,
) : ReadOnlyProperty<LifecycleOwner, Ui> {

    private var uiComponent: Ui? = null

    init {
        if (isEagerComponent) {
            lifecycleOwner.addSafeObserver(buildImmediateLifecycleObserver())
        }
    }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): Ui {
        uiComponent?.let { return it }

        return getOrCreateComponent(thisRef)
    }

    private fun getOrCreateComponent(owner: LifecycleOwner): Ui = synchronized(this) {
        uiComponent?.let {
            return it
        }

        val safeLifecycleOwner = owner
            .safeLifecycleOwner()
            .apply {
                addSafeObserver(buildUiComponentLifecycleObserver())
            }.also {
                if (!it.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    error("Ui Component hasn't initialized yet")
                }
            }

        uiComponent = safeLifecycleOwner.createUiComponent(
            componentCreation,
            owner.rootCurrentView()
        )

        return uiComponent as Ui
    }

    private fun buildUiComponentLifecycleObserver() = UiComponentLifecycleObserver {
        release()
    }

    private fun buildImmediateLifecycleObserver() = ImmediateLifecycleObserver {
        getOrCreateComponent(lifecycleOwner)
    }

    private fun release() = synchronized(this) {
        uiComponent?.release()
        uiComponent = null
    }

}
