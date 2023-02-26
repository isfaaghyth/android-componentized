package app.isfa.component.lib

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner

fun <Ui: BaseUiComponent> LifecycleOwner.component(
    isEagerComponent: Boolean = false,
    componentCreation: (ViewGroup) -> Ui
): UiComponentDelegate<Ui> {
    return UiComponentDelegate(
        lifecycleOwner = this,
        isEagerComponent = isEagerComponent,
        componentCreation = componentCreation,
    )
}
