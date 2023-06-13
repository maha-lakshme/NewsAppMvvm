package com.maha.NewsAppMvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maha.NewsAppMvvm.models.Article
import com.maha.NewsAppMvvm.models.NewsResponse
import com.maha.NewsAppMvvm.repository.NewsRepository
import com.maha.NewsAppMvvm.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewViewModel (val newsRepositorey : NewsRepository) : ViewModel(){
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1

    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsPage = 1
    init {
        getBreakingNews("us")
    }
    fun getBreakingNews(countryCode:String) =  viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val newsresponse = newsRepositorey.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handelBreakingNewsResponse(newsresponse))
    }
    fun searchNews(searchQuery:String)=viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepositorey.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handelSearchNewsResponse(response))
    }
    private fun handelBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handelSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse->
                return Resource.success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article)=viewModelScope.launch {
        newsRepositorey.upsert(article)
    }
    fun savedNews()=newsRepositorey.getSavedNews()
    fun deleteArticle(article: Article)=viewModelScope.launch {
        newsRepositorey.deleteArticel(article)
    }
}
