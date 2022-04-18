package com.langga.movieapp.core.data.source.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.langga.movieapp.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMovie {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getAllMovie(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entity where isFavorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFavoriteMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie_entity where title like :query")
    fun searchMovie(query: String): Flow<List<MovieEntity>>

}