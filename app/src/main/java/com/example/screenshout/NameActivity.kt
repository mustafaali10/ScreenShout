package com.example.screenshout

import AutoResizedText
import androidx.compose.runtime.*
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RotateLeft
import androidx.compose.material.icons.filled.RotateRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.screenshout.ui.theme.ScreenShoutTheme

class NameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScreenShoutTheme {
                // A surface container using the 'background' color from the theme
                val hexColor = intent.getStringExtra("hexColor")
                val color = Color(hexColor!!.toLong(16))
                val enteredText = intent.getStringExtra("name") ?: ""
                val textList = enteredText?.toCharArray()?.map { it.toString() } ?: listOf()

                CheckOrientationNameDisplay(color, enteredText, ::toggleOrientation)

                SideEffect {
                    val window = (this as Activity).window
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    window.statusBarColor = color.toArgb()
                    window.navigationBarColor = color.toArgb()
                }

                hideSystemUI()
            }
        }
    }

    private fun hideSystemUI() {
        // Set flags to enable immersive sticky mode
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

    private fun toggleOrientation() {
        requestedOrientation = if (resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}

@Composable
fun MainDisplay(color: Color, enteredText: String, onRotateClick: () -> Unit) {
    var columnHeight by remember { mutableStateOf(0) }
    val columnWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current
    var visible by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Box {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .onGloballyPositioned { coordinates ->
                    columnHeight = coordinates.size.height
                    columnWidth - coordinates.size.width
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (columnHeight > 0) {
                val availableHeightInDp = with(density) { columnHeight.toDp() }
                val fontSize = (availableHeightInDp.value / (enteredText.length * 1.465)).sp

                enteredText.forEach {char ->

                    AnimatedVisibility(
                        visible = visible,
                        enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
                    ){
                        Text(
                            text = char.toString(),
                            fontSize = fontSize,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }
        }

        // Button for rotation at the top-right corner
        IconButton(
            onClick = { onRotateClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.RotateRight,
                contentDescription = "Rotate Screen",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun LandScapeNameDisplay(color: Color, enteredText: String, onRotateClick: () -> Unit) {
    var columnHeight by remember { mutableStateOf(0) }
    var columnWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .onGloballyPositioned { coordinates ->
                    columnHeight = coordinates.size.height
                    columnWidth = coordinates.size.width
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AutoResizedText(text = enteredText, color = Color.White, style = TextStyle(fontSize = 500.sp))
        }

        // Button for rotation at the top-right corner
        IconButton(
            onClick = { onRotateClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.RotateLeft,
                contentDescription = "Rotate Screen",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun CheckOrientationNameDisplay(color: Color, enteredText: String, onRotateClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        MainDisplay(color, enteredText, onRotateClick)
    } else {
        LandScapeNameDisplay(color, enteredText, onRotateClick)
    }
}



@Composable
fun AutoResizedText2(
    text: String,
    modifier: Modifier = Modifier,
    initialTextStyle: TextStyle,
) {
    var textStyle by remember { mutableStateOf(initialTextStyle) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        style = textStyle,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        modifier = modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.didOverflowHeight) {
                textStyle = textStyle.copy(fontSize = textStyle.fontSize * 0.9)
            } else {
                readyToDraw = true
            }
        }
    )
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,orientation=landscape")
@Composable
fun PreviewName() {
    ScreenShoutTheme {
        LandScapeNameDisplay(color = Color.Gray, enteredText = "MOINCHAs", onRotateClick = {})
    }
}
