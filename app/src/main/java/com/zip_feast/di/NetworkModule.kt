package com.zip_feast.di

import android.content.Context
import android.content.SharedPreferences
import com.zip_feast.data.remote.apiService.UserApi
import com.zip_feast.data.remote.repository.AuthRepository
import com.zip_feast.data.remote.repository.AuthRepositoryImpl
import com.zip_feast.data.remote.repository.UserRepository
import com.zip_feast.presentation.profile.ProfileViewModel
import com.zip_feast.utils.CONSTANTS.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepository(userApi)
    }
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("zip.feast-prefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(sharedPreferences: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(sharedPreferences)
    }
}