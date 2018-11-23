package com.futuremind.daggerutils

import androidx.lifecycle.ViewModel
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(RUNTIME)
@dagger.MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)