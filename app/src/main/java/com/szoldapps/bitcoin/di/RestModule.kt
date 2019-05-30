package com.szoldapps.bitcoin.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.szoldapps.bitcoin.BuildConfig
import com.szoldapps.bitcoin.repository.remote.rest.BlockchainApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

/**
 * Module to provide rest specific classes
 */
@Module
open class RestModule {

    @Provides
    @Reusable
    internal fun provideDefaultOkHttpClient(context: Context): OkHttpClient {
        val cacheDir = File(context.cacheDir.absolutePath, HTTP_CACHE_DIRECTORY_NAME)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .cache(Cache(cacheDir, HTTP_CACHE_SIZE))
            .build()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create()
    }

    @Provides
    @Reusable
    internal fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Reusable
    internal fun provideBlockchainApi(retrofit: Retrofit): BlockchainApi = retrofit.create(BlockchainApi::class.java)

    private fun loggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    companion object {
        private const val BASE_URL = "https://api.blockchain.info"
        private const val HTTP_CACHE_DIRECTORY_NAME = "HttpCache"
        private const val HTTP_CACHE_SIZE = 10 * 1024 * 1024L // 10 MiB
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }
}
