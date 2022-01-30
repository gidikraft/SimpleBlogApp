package com.olamachia.simpleblogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olamachia.simpleblogapp.repository.NewsRepository

class NewsViewModelFactoryProvider(
    private val newsRepository: NewsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}