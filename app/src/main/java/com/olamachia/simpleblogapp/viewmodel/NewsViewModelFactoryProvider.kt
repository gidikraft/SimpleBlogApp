package com.olamachia.simpleblogapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olamachia.simpleblogapp.repository.NewsRepository

class NewsViewModelFactoryProvider(
    val app: Application,
    private val newsRepository: NewsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }
}