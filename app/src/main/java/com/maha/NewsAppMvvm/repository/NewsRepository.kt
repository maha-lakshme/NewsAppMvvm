package com.maha.NewsAppMvvm.repository

import com.maha.NewsAppMvvm.api.RetroffitInstance
import com.maha.NewsAppMvvm.db.ArticleDatabase
import com.maha.NewsAppMvvm.models.Article

//Get data from database and from api
class NewsRepository( val db:ArticleDatabase) {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetroffitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery:String,pageNumber: Int)=
        RetroffitInstance.api.searchNews(searchQuery,pageNumber)

    suspend fun upsert(article:Article)=db.getArticleDao().upsert(article)
    fun getSavedNews()=db.getArticleDao().getArticle()
    suspend fun deleteArticel(article: Article)=db.getArticleDao().deleteArtice(article)
}