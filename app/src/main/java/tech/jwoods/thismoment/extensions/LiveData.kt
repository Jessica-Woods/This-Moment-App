package tech.jwoods.thismoment.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations

// adapted from https://stackoverflow.com/a/57079290
fun <A, B, C, O> LiveData<A>.combineWith(
    bs: LiveData<B>,
    cs: LiveData<C>,
    combine: (A?, B?, C?) -> O
): LiveData<O> {
    val result = MediatorLiveData<O>()
    result.addSource(this) {
        result.value = combine(this.value, bs.value, cs.value)
    }
    result.addSource(bs) {
        result.value = combine(this.value, bs.value, cs.value)
    }
    result.addSource(cs) {
        result.value = combine(this.value, bs.value, cs.value)
    }
    return result
}

// nicer syntax for `distinctUntilChanged`
fun <A> LiveData<A>.distinctUntilChanged(): LiveData<A> {
    return Transformations.distinctUntilChanged(this)
}