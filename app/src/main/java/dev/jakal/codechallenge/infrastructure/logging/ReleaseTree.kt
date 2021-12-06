package dev.jakal.codechallenge.infrastructure.logging

import android.util.Log
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReleaseTree @Inject constructor() : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }
    }
}
