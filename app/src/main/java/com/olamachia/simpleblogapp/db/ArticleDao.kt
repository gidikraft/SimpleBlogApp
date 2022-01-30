package com.olamachia.simpleblogapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.olamachia.simpleblogapp.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}