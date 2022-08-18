package com.bigcake.githubusers.presentation.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bigcake.githubusers.domain.entity.User
import com.bigcake.githubusers.presentation.Screen
import com.bigcake.githubusers.presentation.composable.LabelText

@ExperimentalMaterial3Api
@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LoginFilter(viewModel) {
                viewModel.onLoginFilterTextChanged(it)
            }
            UserList(navController, viewModel)
        }
        if (viewModel.state.error.isNotBlank()) {
            Text(
                text = viewModel.state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
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
fun UserList(navController: NavController, viewModel: UserViewModel) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        val loginFilterText = viewModel.state.loginFilterText
        val users = if (loginFilterText.isEmpty()) {
            viewModel.state.users
        } else {
            viewModel.state.filteredUsers
        }
        items(users, itemContent = { user ->
            UserItem(user = user, onClick = {
                navController.navigate(Screen.UserDetailScreen.route + "/${user.login}")
            })
        })
        if (loginFilterText.isNotEmpty() && users.isEmpty()) {
            item { NoResultNotice(loginFilterText = loginFilterText) }
        }
        if (viewModel.state.isLoading) {
            item { LoadingIndicator() }
        }
    }
    Pagination(listState = listState) {
        if (!viewModel.state.isLoading) {
            viewModel.loadUsers()
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Row(modifier = Modifier.clickable { onClick() }) {
        UserAvatar(user = user)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterVertically)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    user.login,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (user.isSiteAdmin)
                    LabelText(text = "Site Admin")
            }
            Text(
                text = user.id.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun NoResultNotice(loginFilterText: String) {
    Text(
        text = "Login '$loginFilterText' not found",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun LoadingIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
        )
    }
}

@Composable
fun UserAvatar(user: User) {
    AsyncImage(
        model = user.avatar,
        contentDescription = user.login,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(84.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
    )
}

@Composable
fun Pagination(
    listState: LazyListState,
    buffer: Int = 5,
    onLoadMore: () -> Unit
) {
    val loadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }
    LaunchedEffect(loadMore) {
        if (loadMore) {
            onLoadMore()
        }
    }
}