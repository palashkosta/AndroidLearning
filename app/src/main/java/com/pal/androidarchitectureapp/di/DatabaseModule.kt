package com.pal.androidarchitectureapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pal.androidarchitectureapp.db.AppDatabaseCallback
import com.pal.androidarchitectureapp.db.WordDao
import com.pal.androidarchitectureapp.db.WordRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    lateinit var database: WordRoomDatabase

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context,
                        scope: CoroutineScope): WordRoomDatabase {
        database = Room.databaseBuilder(
            appContext,
            WordRoomDatabase::class.java,
            "word_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                AppDatabaseCallback(database, scope).onCreate()
            }
        }).build()

        return database
    }

    @Provides
    fun provideWordDao(database: WordRoomDatabase): WordDao {
        return database.wordDao()
    }

}