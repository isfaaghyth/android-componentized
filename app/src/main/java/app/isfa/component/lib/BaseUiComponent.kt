package app.isfa.component.lib

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LifecycleObserver

interface BaseUiComponent : LifecycleObserver {
    fun container(): View
    fun resources(): Resources
    fun isShown(): Boolean
    fun isHidden(): Boolean
    fun release() = Unit
}
