package app.isfa.component.lib.observer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import app.isfa.component.lib.util.safeLifecycleOwner

class UiComponentLifecycleObserver(
    private val release: () -> Unit
) : DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
        owner.safeLifecycleOwner()
            .lifecycle
            .removeObserver(this)

        release()
    }
}
