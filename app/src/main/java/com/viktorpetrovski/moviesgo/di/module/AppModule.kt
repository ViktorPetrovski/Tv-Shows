package com.viktorpetrovski.moviesgo.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.viktorpetrovski.moviesgo.BuildConfig
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.data.remote.api.TvShowService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Victor on 3/13/18.
 */
@Module(includes = arrayOf(ViewModelModule::class))
class AppModule{

    @Provides
    @Singleton
    internal fun provideContext(application: Application)  = application


    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application):
            // Application reference must come from AppModule.class
            SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    internal fun provideCalligraphyDefaultConfig() = CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proxima_nova.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()

    @Provides
    @Singleton
    fun providesHttpLogginInterceptor() : HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(logging : HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit( okHttpClient: OkHttpClient) = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()


    @Provides
    fun provideTvShowService(retrofit: Retrofit): TvShowService = retrofit.create(TvShowService::class.java)


}