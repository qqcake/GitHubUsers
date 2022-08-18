package com.bigcake.githubusers.presentation.userlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigcake.githubusers.domain.PageResult
import com.bigcake.githubusers.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    var state by mutableStateOf(UserListState())
        private set

    fun loadUsers() {
        getUsersUseCase.invoke(state.nextPageKey, 20).onEach { result ->
            state = when (result) {
                is PageResult.Page -> {
                    val newState = state.copy(
                        users = state.users + result.data,
                        nextPageKey = result.next
                    )
                    newState
                }
                is PageResult.Failure -> {
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