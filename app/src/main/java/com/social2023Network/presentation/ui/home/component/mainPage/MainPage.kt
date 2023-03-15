package com.social2023Network.presentation.ui.home.component.mainPage

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.social2023Network.R
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.home.component.animePage.AnimeSearchField
import com.social2023Network.presentation.ui.home.component.util.Background
import com.social2023Network.presentation.ui.home.component.util.ImageResource

@Composable
fun MainPage(viewModel: HomeViewModel) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val textField = rememberSaveable {
        mutableStateOf("")
    }
    val showBottomSheet = remember { mutableStateOf(false) }

        Column() {
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
                ImageResource(id = R.drawable.baseline_playlist_add_24, size = 70.dp, onImageClicked = {showBottomSheet.value = true})

            }
            ItemStory(items = viewModel.tempList)
            if (showBottomSheet.value) {
                MyBottomSheet(onDismiss = { showBottomSheet.value = false })
            }
        }

}