package com.asrivastava.lunchtime.di

import android.content.Context
import com.asrivastava.lunchtime.common.Constants
import com.asrivastava.lunchtime.data.remote.GooglePlaceApi
import com.asrivastava.lunchtime.data.repository.FavoriteRestaurantRepositoryImpl
import com.asrivastava.lunchtime.data.repository.RestaurantRepositoryImpl
import com.asrivastava.lunchtime.domain.repository.FavoriteRestaurantRepository
import com.asrivastava.lunchtime.domain.repository.RestaurantRepository
import com.asrivastava.lunchtime.domain.util.LocationProvider
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val contentType = ("application/json").toMediaType()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesOkHttpClient(
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(loggingInterceptor)
    }.build()

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideGooglePlaceApi(
        client: OkHttpClient,
    ): GooglePlaceApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(GooglePlaceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRestaurantRepository(api: GooglePlaceApi): RestaurantRepository {
        return RestaurantRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLocationProvider(@ApplicationContext context: Context): LocationProvider {
        return LocationProvider(context = context)
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoriteRestaurantModule {
    @Binds
    abstract fun bindFavoriteRestaurantRepository(
        favoriteRestaurantRepositoryImpl: FavoriteRestaurantRepositoryImpl
    ): FavoriteRestaurantRepository
}
