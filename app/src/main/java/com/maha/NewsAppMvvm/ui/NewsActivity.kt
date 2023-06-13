package com.maha.NewsAppMvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maha.NewsAppMvvm.R
import com.maha.NewsAppMvvm.db.ArticleDatabase
import com.maha.NewsAppMvvm.models.Article
import com.maha.NewsAppMvvm.repository.NewsRepository

class NewsActivity : AppCompatActivity() {
    lateinit var viewModel: NewViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val newsRepository =NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewViewModel::class.java)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
         bottomNavigationView.setupWithNavController(navController)
    }
}