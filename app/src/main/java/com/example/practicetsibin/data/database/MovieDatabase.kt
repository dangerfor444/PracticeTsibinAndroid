package com.example.practicetsibin.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    
    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null
        
        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
