package com.erikriosetiawan.daggerhilttutorial.data.api

import com.erikriosetiawan.daggerhilttutorial.data.model.User
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<User>>
}