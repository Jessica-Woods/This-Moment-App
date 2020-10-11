package tech.jwoods.thismoment.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

// adapted from https://stackoverflow.com/a/57079290
fun <A, B, O> LiveData<A>.combineWith(
    other: LiveData<B>,
    combine: (A?, B?) -> O
): LiveData<O> {
    val result = MediatorLiveData<O>()
    result.addSource(this) {
        result.value = combine(this.value, other.value)
    }
    result.addSource(other) {
        result.value = combine(this.value, other.value)
    }
    return result
}

// nicer syntax for `distinctUntilChanged`
fun <A> LiveData<A>.distinctUntilChanged(): LiveData<A> {
    return Transformations.distinctUntilChanged(this)
}