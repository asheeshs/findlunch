package com.asrivastava.lunchtime.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface FavoriteRestaurantDao {
    @Query("SELECT id FROM favoriterestaurant")
    fun getFavoriteRestaurants(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurant)

    @Query("DELETE FROM favoriterestaurant WHERE id = :id")
    fun deleteFavoriteRestaurant(id: String)
}
