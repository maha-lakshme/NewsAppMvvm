package com.maha.NewsAppMvvm.db

import androidx.room.TypeConverter
import com.maha.NewsAppMvvm.models.Source

class converters {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name : String): Source {
        return Source(name,name)
    }
}