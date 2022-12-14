package com.example.dicodingstoryapp.di

import android.content.SharedPreferences
import com.example.dicodingstoryapp.BuildConfig
import com.example.dicodingstoryapp.data.source.local.utils.PreferencesKey
import com.example.dicodingstoryapp.data.source.remote.services.AuthService
import com.example.dicodingstoryapp.data.source.remote.services.StoryService
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(sharedPref: SharedPreferences): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.BUILD_TYPE != "release") HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor {
                val token = sharedPref.getString(PreferencesKey.TOKEN_KEY, "")

                val req = it.request()
                val newReq = req.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                return@Interceptor it.proceed(newReq)
            })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideStoryService(retrofit: Retrofit): StoryService {
        return retrofit.create(StoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}