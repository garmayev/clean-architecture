package ru.amgcompany.clean.data.login

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.amgcompany.clean.data.common.module.NetworkModule

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {
}