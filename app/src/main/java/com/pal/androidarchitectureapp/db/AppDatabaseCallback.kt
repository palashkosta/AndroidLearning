package com.pal.androidarchitectureapp.db

import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AppDatabaseCallback(
    private val appDatabase: WordRoomDatabase?,
    private val scope: CoroutineScope) :
    RoomDatabase.Callback() {

    fun onCreate() {
        appDatabase?.let { database ->
            scope.launch {
                populateDatabase(database.wordDao())
            }
        }
    }

    private suspend fun populateDatabase(wordDao: WordDao) {
        // Delete all content here.
        wordDao.deleteAll()

        // Add sample words.
        var word = Word(word = "Hello")
        wordDao.insert(word)
        word = Word("World!")
        wordDao.insert(word)

        word = Word("TODO!")
        wordDao.insert(word)

        word = Word("Palash!")
        wordDao.insert(word)

        word = Word("Kosta!")
        wordDao.insert(word)
    }
}