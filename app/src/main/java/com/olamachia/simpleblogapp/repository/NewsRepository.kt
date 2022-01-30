package com.olamachia.simpleblogapp.repository

import com.olamachia.simpleblogapp.api.RetrofitInstance
import com.olamachia.simpleblogapp.db.ArticleDatabase
import com.olamachia.simpleblogapp.models.Article

class NewsRepository(
    private val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(countryCode, pageNumber)

    suspend fun updateOrInsert(article: Article) = db.getArticle().updateOrInsert(article)

    fun getSavedNews() = db.getArticle().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticle().deleteArticle(article)
}