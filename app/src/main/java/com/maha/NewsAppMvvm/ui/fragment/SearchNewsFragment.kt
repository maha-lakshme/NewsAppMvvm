package com.maha.NewsAppMvvm.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maha.NewsAppMvvm.R
import com.maha.NewsAppMvvm.adapters.NewsAdapter
import com.maha.NewsAppMvvm.ui.NewViewModel
import com.maha.NewsAppMvvm.ui.NewsActivity
import com.maha.NewsAppMvvm.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.maha.NewsAppMvvm.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    lateinit var viewModel: NewViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var rvSearchNews : RecyclerView
    lateinit var paginationPrgogressBar : ProgressBar
    lateinit var etSearch:EditText
    val TAG= "Search news"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =(activity as NewsActivity).viewModel
        rvSearchNews=view.findViewById<RecyclerView>(R.id.rvSearchNews)
        paginationPrgogressBar = view.findViewById<ProgressBar>(R.id.paginationProgressBar)
        etSearch = view.findViewById<EditText>(R.id.etSearch)
        setuprecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("Article",it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleNewsFragment,bundle)
        }

        var job : Job?=null
        etSearch.addTextChangedListener{editable->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
               editable?.let {
                   if(editable.toString().isNotEmpty()){
                       viewModel.searchNews(editable.toString())
                   }
               }
            }
        }



        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response->
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
private fun setuprecyclerView() {
    newsAdapter = NewsAdapter()
    rvSearchNews.apply {
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}
}