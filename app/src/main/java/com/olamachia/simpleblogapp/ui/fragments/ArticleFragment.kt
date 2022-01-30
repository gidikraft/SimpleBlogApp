package com.olamachia.simpleblogapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.olamachia.simpleblogapp.databinding.FragmentArticleBinding
import com.olamachia.simpleblogapp.ui.NewsActivity
import com.olamachia.simpleblogapp.viewmodel.NewsViewModel

class ArticleFragment : Fragment() {
    private var _binding : FragmentArticleBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: NewsViewModel
//    private val args : ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

//        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(viewModel.webViewUrl)
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(viewModel.webArticle)
            Snackbar.make(view, "Article saved successful", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}