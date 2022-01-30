package com.olamachia.simpleblogapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.olamachia.simpleblogapp.R
import com.olamachia.simpleblogapp.adapters.NewsAdapter
import com.olamachia.simpleblogapp.databinding.FragmentSavedNewsBinding
import com.olamachia.simpleblogapp.ui.NewsActivity
import com.olamachia.simpleblogapp.viewmodel.NewsViewModel

class SavedNewsFragment : Fragment() {
    private var _binding : FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
//            val bundle = Bundle().apply{
//                putSerializable("article", it)
//            }
//            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment, bundle)

            viewModel.webArticle = it
            viewModel.webViewUrl = it.url.toString()
            replaceFragment(ArticleFragment())
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo Delete"){
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.addToBackStack("fragment")
        transaction.commit()
    }

    private fun setUpRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}