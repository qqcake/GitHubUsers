package com.bigcake.githubusers.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
    var state by mutableStateOf(UserState())
        private set

    fun loadUsers() {
        getUserByPage.invoke(state.nextPageKey, 20).onEach { result ->
            state = when (result) {
                is Result.Page -> {
                    val newState = state.copy(
                        users = state.users + result.data,
                        nextPageKey = result.next
                    )
                    newState
                }
                is Result.Failure -> {
                    state.copy(error = result.message)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onLoginFilterTextChanged(text: String) {
        state = if (text.isEmpty()) {
            state.copy(loginFilterText = "", filteredUsers = emptyList())
        } else {
            state.copy(
                loginFilterText = text,
                filteredUsers = state.users.filter { user -> user.login.contains(text) }
            )
        }
    }
}