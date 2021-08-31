package com.demoapp.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*


/**
 * Generic fragment extension function to be able to retrieve the viewModel given a [T] class.
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit = {}): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}


inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit = {}): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

/**
 * Method to simplified how to set an Observer by just passing the [body] to be executed inside the observer.
 */
inline fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, crossinline body: (T?) -> Unit) =
    liveData.observe(this, Observer<T?> { t -> body(t) })

fun <T : Any, L : LiveData<T>> LifecycleOwner.unObserve(liveData: L) = liveData.removeObservers(this)

/**
 * We need to restart the observer because it stays observing until the LifecycleOwner is destroyed.
 * In the case of Fragments, they are not destroyed when the fragment is detached/reattached, and a new
 * observer could be added every time the Fragment is shown and onActivityCreated() is executed.
 * By doing this, we make sure we only have ONE observer at a time.
 * Refer to: https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
 */
inline fun <T : Any, L : LiveData<T>> LifecycleOwner.reObserve(liveData: L, crossinline body: (T?) -> Unit) {
    unObserve(liveData)
    observe(liveData, body)
}
