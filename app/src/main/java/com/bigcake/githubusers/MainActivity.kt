package com.bigcake.githubusers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bigcake.githubusers.ui.theme.GitHubUsersTheme
import com.bigcake.githubusers.ui.theme.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubUsersTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen()
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun UserScreen(viewModel: UserViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        LoginFilter(viewModel) {
            viewModel.onLoginFilterTextChanged(it)
        }
        UserList(viewModel)
    }
}

@ExperimentalMaterial3Api
@Composable
fun LoginFilter(
    viewModel: UserViewModel,
    onLoginFilterTextChanged: (String) -> Unit
) {
    TextField(
        value = viewModel.state.loginFilterText,
        onValueChange = onLoginFilterTextChanged,
        label = { Text(text = "Filter by login") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun UserList(viewModel: UserViewModel) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        val loginFilterText = viewModel.state.loginFilterText
        val filteredUsers = if (loginFilterText.isEmpty()) {
            viewModel.state.users
        } else {
            viewModel.state.users.filter { user -> user.login.contains(loginFilterText) }
        }
        items(filteredUsers.size) { i ->
            val user = filteredUsers[i]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = user.id.toString(),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(user.login)
            }

        }
    }
    val loadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItemsNumber - 2)
        }
    }
    LaunchedEffect(loadMore) {
        Log.d("Martin", "loadMore=$loadMore")
        if (loadMore) {
            viewModel.loadUsers()
        }
    }
}