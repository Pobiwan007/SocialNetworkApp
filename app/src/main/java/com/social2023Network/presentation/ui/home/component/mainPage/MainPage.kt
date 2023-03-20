package com.social2023Network.presentation.ui.home.component.mainPage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.social2023Network.R
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.animePage.AnimeSearchField
import com.social2023Network.presentation.ui.home.component.util.ImageResource

@Composable
fun MainPage(viewModel: HomeViewModel) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val textField = rememberSaveable {
        mutableStateOf("")
    }
    val showBottomSheet = remember { mutableStateOf(false) }
    val stories by viewModel.stories.collectAsState()
    val lazyListState = rememberLazyListState()
    val posts by viewModel.posts.collectAsState()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimeSearchField(
                textField = textField,
                onSearch = {},
                lifecycleScope = lifecycleScope,
                modifier = Modifier
                    .fillMaxWidth(.85f)
                    .padding(10.dp),
            )
            ImageResource(
                id = R.drawable.baseline_playlist_add_24,
                size = 70.dp,
                onImageClicked = { showBottomSheet.value = true })

        }
        ItemStory(items = stories)
        if (showBottomSheet.value) {
            MyBottomSheet(
                onDismiss = { showBottomSheet.value = false },
                onPublish =  {post, listUri ->
                    showBottomSheet.value = false
                    viewModel.createNewPostInFirebase(post, listUri)
                },
                viewModel
            )
        }
        LazyColumn(state = lazyListState, modifier = Modifier.fillMaxWidth()) {
            items(posts) {
                Posts(post = it)
            }
        }
    }

}