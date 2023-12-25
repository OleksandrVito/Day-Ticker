package ua.vitolex.dayscounter.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.vitolex.dayscounter.db.MainDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDatabase(app: Application): MainDatabase {
        return Room.databaseBuilder(
            app,
            MainDatabase::class.java,
            "main_db"
        ).build()
    }
}