package dev.jakal.codechallenge.ui

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import dev.jakal.codechallenge.BuildConfig
import dev.jakal.codechallenge.infrastructure.logging.ReleaseTree
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class CodeChallengeApplication : Application() {

    @Inject
    lateinit var releaseTree: ReleaseTree

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else releaseTree)
    }
}
