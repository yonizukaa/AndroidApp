package com.example.aboutme.network

import com.example.aboutme.tasklist.Task
import retrofit2.Response
import retrofit2.http.GET

interface TaskWebService {
    @GET("tasks")
    suspend fun getTasks(): Response<List<Task>>
}