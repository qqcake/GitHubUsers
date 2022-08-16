package com.bigcake.githubusers.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigcake.githubusers.domain.entity.Result
import com.bigcake.githubusers.domain.usecase.GetUserByPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserByPage: GetUserByPage
) : ViewModel() {
    //    private val _state = mutableStateOf(UserState())
    var state by mutableStateOf(UserState())
        private set


    fun loadUsers() {
        getUserByPage.invoke(state.nextPageKey, 20).onEach { result ->
            when (result) {
                is Result.Page -> {
                    val newState = state.copy(
                        users = state.users + result.data,
                        nextPageKey = result.next
                    )
                    Log.d("Martin", "xxx ${newState.users.size}")
                    state = newState
                }
                is Result.Failure -> {
                    Log.d("Martin", "yyy")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onLoginFilterTextChanged(text: String) {
        state = state.copy(loginFilterText = text)
    }
}