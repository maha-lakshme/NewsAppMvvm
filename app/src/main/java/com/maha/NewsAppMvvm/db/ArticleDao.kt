package com.maha.NewsAppMvvm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maha.NewsAppMvvm.models.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long

    //not using suspend function bec it returns livedata obj which will update all the
    // subscribed fragemnt and function whenevere the data changes it will notify all the observers the
    // changes in this case recycle view useful in case of device rotation in view model not recreated
    // so the livedata is not recreated
    @Query("SELECT * FROM articles")
    fun getArticle():LiveData<List<Article>>

    @Delete
    suspend fun deleteArtice(article: Article)
}