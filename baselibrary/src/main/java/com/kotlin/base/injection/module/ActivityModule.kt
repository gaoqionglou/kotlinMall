package com.kotlin.base.injection.module

import android.app.Activity
import com.kotlin.base.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun providesActivity(): Activity {
        return activity
    }
}