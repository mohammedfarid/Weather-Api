package com.farid.weatherlogger.network

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response


abstract class BaseRepository<S>(@PublishedApi internal val service: S,application: Application) {
    inline fun <reified T : Any> fetchData(crossinline call: (S) -> Deferred<Response<T>>): LiveData<T> {
        val result = MutableLiveData<T>()
        CoroutineScope(Dispatchers.IO).launch {
            val request = call(service)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        result.value = response.body()
                    } else {
                        Log.d("BaseRepository", "Error occurred with code ${response.code()}")
                    }
                } catch (e: HttpException) {
                    Log.d("BaseRepository", "Error: ${e.message()}")
                } catch (e: Throwable) {
                    Log.d("BaseRepository", "Error: ${e.message}")
                }
            }
        }
        return result
    }
}