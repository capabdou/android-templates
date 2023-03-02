package co.nimblehq.sample.xml.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import co.nimblehq.sample.xml.data.repository.AppPreferencesRepositoryImpl
import co.nimblehq.sample.xml.domain.repository.AppPreferencesRepository
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindAppPreferencesRepository(
        appPreferencesRepositoryImpl: AppPreferencesRepositoryImpl
    ): AppPreferencesRepository

    companion object {
        private val Context.appPreferencesDataStore by preferencesDataStore("app_preferences")

        @Provides
        fun provideAppPreferencesDataStore(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> = applicationContext.appPreferencesDataStore
    }
}
