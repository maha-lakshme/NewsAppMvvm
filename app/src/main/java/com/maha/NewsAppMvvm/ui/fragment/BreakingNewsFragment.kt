package com.maha.NewsAppMvvm.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maha.NewsAppMvvm.R
import com.maha.NewsAppMvvm.adapters.NewsAdapter
import com.maha.NewsAppMvvm.ui.NewViewModel
import com.maha.NewsAppMvvm.ui.NewsActivity
import com.maha.NewsAppMvvm.util.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var rvBreakingNews :RecyclerView
    lateinit var paginationPrgogressBar : ProgressBar
    val TAG= "Breaking news"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =(activity as NewsActivity).viewModel
        rvBreakingNews=view.findViewById<RecyclerView>(R.id.rvBreakingNews)
        paginationPrgogressBar = view.findViewById<ProgressBar>(R.id.paginationProgressBar)
        setuprecyclerView()

        newsAdapter.setOnItemClickListener {
            Log.d(TAG,"item clicked")
            val bundle = Bundle().apply {
                putSerializable("Article",it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleNewsFragment,bundle)
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
            when(response){
                is Resource.success ->{
                    hidePrgressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error ->{
                    hidePrgressBar()
                    response.data?.let {
                        hidePrgressBar()
                        response.message?.let {message->
                            Log.e(TAG,"An error occured;$message")

                        }
                    }

                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }

        })
    }
    private fun  hidePrgressBar(){
        paginationPrgogressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar(){
        paginationPrgogressBar.visibility = View.VISIBLE
    }
    private fun setuprecyclerView(){
        newsAdapter = NewsAdapter()
       rvBreakingNews.apply{
           adapter = newsAdapter
           layoutManager = LinearLayoutManager(activity)
       }
    }
}