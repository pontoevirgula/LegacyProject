package com.chslcompany.legacyproject.framework.data.di

import com.chslcompany.legacyproject.framework.data.remote.ContactApi
import com.chslcompany.legacyproject.framework.data.repository.ContactRepository
import com.chslcompany.legacyproject.framework.data.repository.ContactRepositoryImpl
import com.chslcompany.legacyproject.ui.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideContactRepository(
        api: ContactApi
    ): ContactRepository = ContactRepositoryImpl(api)


    @Provides
    fun provideDispatcher() : DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }


}