package com.langga.movieapp.core.data.source

import com.langga.movieapp.core.data.source.local.LocalDataSource
import com.langga.movieapp.core.data.source.remote.RemoteDataSource
import com.langga.movieapp.core.data.source.remote.network.ApiResponse
import com.langga.movieapp.core.data.source.remote.response.ResultMovie
import com.langga.movieapp.core.domain.model.Movie
import com.langga.movieapp.core.domain.repository.IMovieRepository
import com.langga.movieapp.core.utils.AppExecutors
import com.langga.movieapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IMovieRepository {

    override fun getAllMovie(query: String): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<ResultMovie>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie(query).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultMovie>>> {
                return remoteDataSource.getAllMovie()
            }

            override suspend fun saveCallResult(data: List<ResultMovie>) {
                val listMovie = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovie(listMovie)
            }
        }.asFlow()
    }


    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }

    override fun searchMovie(query: String): Flow<List<Movie>> {
        return localDataSource.searchMovie(query).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
}
