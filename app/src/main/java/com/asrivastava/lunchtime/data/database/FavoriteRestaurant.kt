package com.asrivastava.lunchtime.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteRestaurant(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
)
