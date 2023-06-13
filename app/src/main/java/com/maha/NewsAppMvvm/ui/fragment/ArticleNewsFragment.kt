package com.maha.NewsAppMvvm.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.maha.NewsAppMvvm.R
import com.maha.NewsAppMvvm.ui.NewViewModel
import com.maha.NewsAppMvvm.ui.NewsActivity

class ArticleNewsFragment : Fragment(R.layout.fragment_article) {
    lateinit var viewModel: NewViewModel
    val args: ArticleNewsFragmentArgs by navArgs()
    lateinit var webView:WebView
    lateinit var fab:FloatingActionButton
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =(activity as NewsActivity).viewModel
        webView = view.findViewById<WebView>(R.id.webView)
        fab=view.findViewById<FloatingActionButton>(R.id.fab)
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
        fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article saved!!",Snackbar.LENGTH_SHORT).show()
        }
    }
}