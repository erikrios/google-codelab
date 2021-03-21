package com.erikriosetiawan.daggerhilttutorial.data.repository

import com.erikriosetiawan.daggerhilttutorial.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}