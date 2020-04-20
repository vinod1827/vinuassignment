package com.vinu.vinodassigment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vinu.vinodassigment.dao.NewsDao
import com.vinu.vinodassigment.models.ResponseModel
import com.vinu.vinodassigment.utils.Converters

@Database(entities = [ResponseModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        var TEST_MODE = false

        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NewsRoomDatabase? = null

        fun getDatabase(context: Context): NewsRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance: NewsRoomDatabase?
                if (TEST_MODE) {
                    instance = Room.inMemoryDatabaseBuilder(context, NewsRoomDatabase::class.java)
                        .allowMainThreadQueries().build()
                } else {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsRoomDatabase::class.java,
                        "news_database"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}