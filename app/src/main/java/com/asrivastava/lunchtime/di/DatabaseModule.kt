package com.asrivastava.lunchtime.di

import android.content.Context
import com.asrivastava.lunchtime.data.database.AppDatabase
import com.asrivastava.lunchtime.data.database.FavoriteRestaurantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideFavoriteRestaurantDao(appDatabase: AppDatabase): FavoriteRestaurantDao {
        return appDatabase.favoriteRestaurantDao()
    }
}
