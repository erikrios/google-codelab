package com.erikriosetiawan.daggerhilttutorial.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikriosetiawan.daggerhilttutorial.data.model.User
import com.erikriosetiawan.daggerhilttutorial.data.repository.MainRepository
import com.erikriosetiawan.daggerhilttutorial.utils.NetworkHelper
import com.erikriosetiawan.daggerhilttutorial.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.value = Resource.loading(null)
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getUsers().let {
                    if (it.isSuccessful) {
                        _users.value = Resource.success(it.body())
                    } else _users.value = Resource.error(it.errorBody().toString(), null)
                }
            } else _users.value = Resource.error("No internet connection", null)
        }
    }
}