package com.maha.NewsAppMvvm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.maha.NewsAppMvvm.R
import com.maha.NewsAppMvvm.adapters.NewsAdapter
import com.maha.NewsAppMvvm.ui.NewViewModel
import com.maha.NewsAppMvvm.ui.NewsActivity

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var rvSavedNews : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =(activity as NewsActivity).viewModel
        rvSavedNews=view.findViewById<RecyclerView>(R.id.rvSavedNews)
        setuprecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("Article",it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleNewsFragment,bundle)
        }
         val itemTouchHelperCallback= object :ItemTouchHelper.SimpleCallback(
             ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
         ){
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
                 Snackbar.make(view,"sucessfully deleted article",Snackbar.LENGTH_LONG).apply {
                     setAction("undo"){
                         viewModel.saveArticle(article)
                     }
                     show()
                 }
             }

         }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
            viewModel.savedNews().observe(viewLifecycleOwner, Observer { articles ->
                newsAdapter.differ.submitList(articles)

        })
    }

    private fun setuprecyclerView(){
        newsAdapter = NewsAdapter()
        rvSavedNews.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}