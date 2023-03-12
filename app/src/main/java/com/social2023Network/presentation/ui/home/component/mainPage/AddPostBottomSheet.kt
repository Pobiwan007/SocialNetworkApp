package com.social2023Network.presentation.ui.home.component.mainPage

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.social2023Network.R
import com.social2023Network.presentation.ui.theme.colorRed
import com.social2023Network.presentation.ui.theme.coral
import com.social2023Network.presentation.ui.theme.green
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyBottomSheet(onDismiss: () -> Unit) {
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scope = rememberCoroutineScope()
    val insets = LocalConfiguration.current

    val animateHeight = animateFloatAsState(
        if (bottomSheetState.isCollapsed) 0f else 0.3f,
        tween(durationMillis = 300, easing = FastOutSlowInEasing),
    )
    val textField = rememberSaveable {
        mutableStateOf("")
    }
    val state = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    BottomSheetScaffold(
        scaffoldState = state,
        modifier = Modifier
            .padding(10.dp)
            .alpha(.5f)
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
                            bottomSheetState.expand()
                            onDismiss()
                        }
                    },
                    textOnButton = stringResource(id = R.string.cancel),
                    colorText = colorRed
                )
                ButtonPost(
                    onButtonClicked = {
                        scope.launch {
                            bottomSheetState.expand()
                            onDismiss()
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
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NewsPostTextField(
                    textField = textField,
                    onValueChange = { textField.value = it })
            }
        }
    )

}

@Composable
fun NewsPostTextField(
    textField: MutableState<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textField.value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        label = {
            Text(
                text = stringResource(id = R.string.label_news_post),
                style = TextStyle(color = Color.White)
            )
        },
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