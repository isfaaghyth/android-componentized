package app.isfa.component.lib.observer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import app.isfa.component.lib.util.safeLifecycleOwner

class ImmediateLifecycleObserver(
    private val componentCreation: () -> Unit
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        try {
            componentCreation()
        } catch (t: Throwable) {
            throw t
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.safeLifecycleOwner()
            .lifecycle
            .removeObserver(this)
    }
}
