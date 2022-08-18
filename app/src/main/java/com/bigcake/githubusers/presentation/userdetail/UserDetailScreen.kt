package com.bigcake.githubusers.presentation.userdetail

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bigcake.githubusers.domain.entity.UserDetail
import com.bigcake.githubusers.presentation.composable.LabelText

@Composable
fun UserDetailScreen(viewModel: UserDetailViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    val state = viewModel.state
                    state.user?.also { user ->
                        UserAvatar(
                            user = user,
                            containerHeight = this@BoxWithConstraints.maxWidth
                        )
                        UserNameAndBio(user)
                        DetailInfo(label = "GitHub ID", value = user.gitHubId)
                        DetailInfo(label = "Location", value = user.location)
                        DetailInfo(label = "Blog", value = user.blog)
                    }
                }
            }
        }
    }
}

@Composable
fun UserAvatar(
    user: UserDetail,
    containerHeight: Dp
) {
    AsyncImage(
        model = user.avatar,
        modifier = Modifier
            .height(containerHeight)
            .fillMaxWidth(),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
fun UserNameAndBio(user: UserDetail) {
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = user.name ?: "This user has no name",
                style = MaterialTheme.typography.headlineMedium,
            )
            if (user.isSiteAdmin) {
                Spacer(modifier = Modifier.width(8.dp))
                LabelText(text = "Site Admin")
            }
        }
        val bio = user.bio
        if (bio != null && bio.isNotEmpty()) {
            Text(text = bio, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DetailInfo(label: String, value: String?) {
    value?.takeIf { it.isNotEmpty() }?.let {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Divider(modifier = Modifier.padding(bottom = 16.dp))
            Text(
                text = label,
                modifier = Modifier.height(24.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                modifier = Modifier.height(24.dp),
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Visible
            )
        }
    }
}