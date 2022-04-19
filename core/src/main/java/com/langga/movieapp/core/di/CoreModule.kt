package com.langga.movieapp.core.di


import androidx.room.Room
import com.langga.movieapp.core.BuildConfig
import com.langga.movieapp.core.data.source.MovieRepository
import com.langga.movieapp.core.data.source.local.LocalDataSource
import com.langga.movieapp.core.data.source.local.room.MovieDatabase
import com.langga.movieapp.core.data.source.remote.RemoteDataSource
import com.langga.movieapp.core.data.source.remote.network.ApiService
import com.langga.movieapp.core.domain.repository.IMovieRepository
import com.langga.movieapp.core.utils.AppExecutors
import com.langga.movieapp.core.utils.Network
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().daoMovie() }
    single {
        val passphrase :ByteArray = SQLiteDatabase.getBytes("movieDB_APP".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "movie.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}


val networkModule = module {
    single {
        val hostname = Network.HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, Network.SHA1)
            .add(hostname, Network.SHA2)
            .add(hostname, Network.SHA3)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}


val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(
            get(),
            get(),
            get()
        )
    }
}