package com.asrivastava.lunchtime.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asrivastava.lunchtime.common.Constants.DATABASE_NAME

@Database(entities = [FavoriteRestaurant::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteRestaurantDao(): FavoriteRestaurantDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}
