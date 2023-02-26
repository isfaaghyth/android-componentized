package app.isfa.component.lib

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes

open class UiComponent constructor(
    private val container: ViewGroup,
    @IdRes val componentId: Int,
) : BaseUiComponent {

    protected val context: Context by lazy {
        container.context
    }

    protected fun <V: View> findViewById(
        @IdRes id: Int
    ) = lazy(LazyThreadSafetyMode.NONE) {
        container().findViewById(id) as V
    }.value

    override fun container(): View {
        return container.findViewById(componentId)
    }

    override fun resources(): Resources {
        return container().resources
    }

    override fun isShown(): Boolean {
        return container().visibility == View.VISIBLE
    }

    override fun isHidden(): Boolean {
        return container().visibility == View.GONE
    }
}
