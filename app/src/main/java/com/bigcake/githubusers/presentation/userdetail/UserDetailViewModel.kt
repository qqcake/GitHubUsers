package com.bigcake.githubusers.presentation.userdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bigcake.githubusers.domain.Result
import com.bigcake.githubusers.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var state by mutableStateOf(UserDetailState())
        private set

    init {
        savedStateHandle.get<String>("userLogin")?.let { userLogin ->
            getUserDetail(userLogin)
        }
    }

    private fun getUserDetail(userLogin: String) {
        getUserDetailUseCase.invoke(userLogin)
            .onEach { result ->
                state = when (result) {
                    is Result.Success -> {
                        state.copy(user = result.data)
                    }
                    is Result.Failure -> {
                        state.copy(error = result.message)
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}