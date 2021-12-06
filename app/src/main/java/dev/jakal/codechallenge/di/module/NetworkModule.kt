package dev.jakal.codechallenge.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.jakal.codechallenge.BuildConfig
import dev.jakal.codechallenge.infrastructure.common.network.ApiTypeAdapter
import dev.jakal.codechallenge.infrastructure.common.network.CodeChallengeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun retrofit(
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()

    @Provides
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(HTTP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    @Provides
    fun converterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    fun moshi(apiTypeAdapter: ApiTypeAdapter): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(apiTypeAdapter)
        .build()

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    fun preferenceApi(retrofit: Retrofit): CodeChallengeApi =
        retrofit.create(CodeChallengeApi::class.java)

    companion object {
        private const val HTTP_TIMEOUT_SECONDS = 10L
        private const val BASE_URL = "https://us-central1-dazn-sandbox.cloudfunctions.net"
    }
}
