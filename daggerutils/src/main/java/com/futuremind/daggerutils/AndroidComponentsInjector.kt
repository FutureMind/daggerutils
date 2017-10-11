package com.tradewize.dagger

import dagger.android.support.AndroidSupportInjection
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import dagger.android.AndroidInjection
import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Based on https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/di/AppInjector.java
 *
 * Helper class to automatically inject activities and fragments when they implement [Injectable].
 */
object AndroidComponentsInjector {
    fun init(app: Application) {

        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = injectActivity(activity)
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    /**
     * Inject [Injectable] activity when it's created
     */
    private fun injectActivity(activity: Activity) {
        if (activity is Injectable) AndroidInjection.inject(activity)
        injectFragments(activity)
    }

    /**
     * Inject [Injectable] fragments when they're attached.
     * @Note: Only supports Support Fragments for now, but it can be easily changed later.
     */
    private fun injectFragments(activity: Activity) {
        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentAttached(fm: FragmentManager, fragment: Fragment, context: Context) {
                if (fragment is Injectable) AndroidSupportInjection.inject(fragment)
                super.onFragmentAttached(fm, fragment, context)
            }
        }, true)

    }
}