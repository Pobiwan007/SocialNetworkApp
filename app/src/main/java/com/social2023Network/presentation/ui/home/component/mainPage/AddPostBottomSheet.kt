package com.social2023Network.presentation.ui.home.component.mainPage

import android.net.Uri
import android.os.Build
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.social2023Network.R
import com.social2023Network.domain.model.post.Post
import com.social2023Network.presentation.ui.home.HomeViewModel
import com.social2023Network.presentation.ui.util.component.ImageResource
import com.social2023Network.presentation.ui.theme.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@Composable
fun MyBottomSheet(
    onDismiss: () -> Unit,
    onPublish: suspend (post: Post, listUri: SnapshotStateList<Uri?>) -> Unit,
    viewModel: HomeViewModel
) {
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scope = rememberCoroutineScope()
    val insets = LocalConfiguration.current
    val dismissState = remember {
        mutableStateOf(false)
    }
    val collapsedValue = if (bottomSheetState.isCollapsed) 0f else 0.4f
    val expandedValue = if (bottomSheetState.isCollapsed) 0.4f else 0f

    val animateHeight = animateFloatAsState(
        if (!dismissState.value) collapsedValue else expandedValue,
        tween(durationMillis = 700, easing = FastOutSlowInEasing)
    )
    val titleTextField = remember { mutableStateOf("") }
    val descTextField = remember { mutableStateOf("") }
    val listURI = remember {
        mutableStateListOf<Uri?>()
    }
    val state = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    BottomSheetScaffold(
        scaffoldState = state,
        modifier = Modifier
            .padding(10.dp)
            .height((animateHeight.value * insets.screenHeightDp).dp)
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .clip(MaterialTheme.shapes.large),
        backgroundColor = coral,
        sheetContent = {
            Row() {
                ButtonPost(
                    onButtonClicked = {
                        scope.launch {
                            dismissState.value = true
                            delay(700)
                            onDismiss()
                        }
                    },
                    textOnButton = stringResource(id = R.string.cancel),
                    colorText = colorRed
                )
                ButtonPost(
                    onButtonClicked = {
                        scope.launch {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                val currentPost = Post(
                                    "1",
                                    time = LocalDateTime.now().toString(),
                                    desc = descTextField.value,
                                    title = titleTextField.value
                                )
                                if (currentPost.validateData()) {
                                    dismissState.value = true
                                    delay(700)
                                    onPublish(currentPost, listURI)
                                }
                            }
                        }
                    },
                    textOnButton = stringResource(id = R.string.submit),
                    colorText = green
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorBlue)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NewsPostTextField(
                        textField = titleTextField,
                        onValueChange = { titleTextField.value = it },
                        textTitle = stringResource(id = R.string.title_news_post),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(5.dp)
                    )
                    ImageResource(id = R.drawable.baseline_image_24, size = 50.dp) {
                        viewModel.permissionsManager.selectImage {
                            GlobalScope.launch {
                                listURI.add((it))
                            }
                        }
                    }
                    ImageResource(id = R.drawable.baseline_library_music_24, size = 50.dp) {

                    }
                }
                if (listURI.isNotEmpty())
                    ShowImagesListUri(
                        listURI = listURI, modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                    )

                NewsPostTextField(
                    textField = descTextField,
                    onValueChange = { descTextField.value = it },
                    textTitle = stringResource(id = R.string.desc_news_post),
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(pink)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }

        }
    )

}

@Composable
fun NewsPostTextField(
    textField: MutableState<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textTitle: String? = "",
) {
    TextField(
        value = textField.value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                text = textTitle!!,
                style = TextStyle(color = Color.White)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.White,
            disabledLabelColor = colorRed,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White
        ),
    )
}


@Composable
fun ButtonPost(
    onButtonClicked: () -> Unit,
    textOnButton: String,
    colorText: Color,
) {
    Button(
        modifier = Modifier
            .width((LocalConfiguration.current.screenWidthDp / 2).dp)
            .border(1.dp, Color.Transparent, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.Gray
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(vertical = 5.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 3.dp,
            pressedElevation = 6.dp,
            disabledElevation = 0.dp
        ),
        onClick = {
            onButtonClicked()
        },
        content = {
            Text(
                text = textOnButton,
                style = TextStyle(fontSize = 20.sp, color = colorText),
                modifier = Modifier.padding(5.dp)
            )
        }
    )
}

@Composable
fun ShowImagesListUri(listURI: SnapshotStateList<Uri?>, modifier: Modifier) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (item in listURI) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(6.dp), contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = item,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.4f),
                        contentScale = ContentScale.Crop
                    )
                    ImageResource(id = R.drawable.baseline_close_24, size = 50.dp) {
                        listURI.removeAt(item.toString().indexOf(item.toString()))
                    }
                }
            }
        }
    }
}