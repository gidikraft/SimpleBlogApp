package com.olamachia.simpleblogapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.databinding.ActivityNewsBinding
import com.olamachia.simpleblogapp.db.ArticleDatabase
import com.olamachia.simpleblogapp.repository.NewsRepository
import com.olamachia.simpleblogapp.ui.fragments.BreakingNewsFragment
import com.olamachia.simpleblogapp.ui.fragments.SavedNewsFragment
import com.olamachia.simpleblogapp.ui.fragments.SearchNewsFragment
import com.olamachia.simpleblogapp.viewmodel.NewsViewModel
import com.olamachia.simpleblogapp.viewmodel.NewsViewModelFactoryProvider

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelFactoryProvider(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

//        val newsNavHostFragment = binding.newsNavHostFragment
//        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.news_nav_host_fragment))

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.breakingNewsFragment -> {
                    replaceFragment(BreakingNewsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.savedNewsFragment -> {
                    replaceFragment(SavedNewsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.searchNewsFragment -> {
                    replaceFragment(SearchNewsFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        replaceFragment(BreakingNewsFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.addToBackStack("fragment")
        transaction.commit()
    }
}