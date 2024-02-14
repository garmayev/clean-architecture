package ru.amgcompany.clean.data.common.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.amgcompany.clean.utils.SharedPrefs

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefsModule {
    @Provides
    fun provideSharedPref(@ApplicationContext context : Context) :SharedPrefs {
        return SharedPrefs(context)
    }
}