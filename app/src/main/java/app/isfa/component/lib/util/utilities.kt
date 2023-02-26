package app.isfa.component.lib.util

import android.app.Activity
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import app.isfa.component.lib.BaseUiComponent

fun LifecycleOwner.safeLifecycleOwner(): LifecycleOwner {
    if (this is Fragment) return this.viewLifecycleOwner
    return this
}

fun <Ui: BaseUiComponent> LifecycleOwner.createUiComponent(
    creation: (ViewGroup) -> Ui,
    viewGroup: ViewGroup
): Ui {
    return creation(viewGroup).also {
        lifecycle.addObserver(it)
    }
}

fun LifecycleOwner.addSafeObserver(observer: LifecycleObserver) {
    if (this is Fragment) return viewLifecycleOwnerLiveData.observe(this) {
        viewLifecycleOwner.lifecycle.addObserver(observer)
    }

    return lifecycle.addObserver(observer)
}

fun LifecycleOwner.rootCurrentView(): ViewGroup {
    val rootView = when (this) {
        is Fragment -> this.requireView()
        is Activity -> (this.findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        else -> error("The type of this owner is not supported yet.")
    }

    return rootView as ViewGroup
}
