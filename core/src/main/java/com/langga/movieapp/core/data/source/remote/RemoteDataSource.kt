package com.langga.movieapp.core.data.source.remote

import android.util.Log
import com.langga.movieapp.core.BuildConfig
import com.langga.movieapp.core.data.source.remote.network.ApiResponse
import com.langga.movieapp.core.data.source.remote.network.ApiService
import com.langga.movieapp.core.data.source.remote.response.ResultMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovie(): Flow<ApiResponse<List<ResultMovie>>> {
        return flow {
            try {
                val response = apiService.getDataMovies(BuildConfig.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}