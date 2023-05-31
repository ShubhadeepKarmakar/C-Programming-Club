package com.example.cprogrammingclub.global

import android.content.Context
import com.example.usertodatabase.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import io.appwrite.services.Databases
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun createDatabase(@ApplicationContext appContext: Context): Databases = Databases(
        Client(appContext)
            .setEndpoint(Constants.ENDPOINT)
            .setProject(Constants.PROJECT_ID)
            .setSelfSigned(true))

}