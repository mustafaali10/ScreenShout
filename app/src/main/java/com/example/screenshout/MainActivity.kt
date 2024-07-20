package com.example.screenshout

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.screenshout.ui.theme.LandscapeMainScreen
import com.example.screenshout.ui.theme.ScreenShoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {


            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color.Transparent

            ) {
                val gradient = Brush.horizontalGradient(

                    colors = listOf(Color.Blue, Color(0xFF2929AA),Color(0xFF0F0F74),Color(0xFF1E1E7C)),

//                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
//                    end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
                val gradientBlack = Brush.linearGradient(
                    colors = listOf(Color(0xFF04040C), Color(0xFF34344D)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = gradient)
                        //.background(Color.Black)
                ) {
                    CheckOrientation()
                }


                SideEffect {
                    val window = (this as Activity).window
                    window.statusBarColor = android.graphics.Color.TRANSPARENT
                    window.navigationBarColor = android.graphics.Color.TRANSPARENT
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                }
            }
            }

        hideSystemUI()


        }

    private fun hideSystemUI() {
        // Set flags to enable immersive sticky mode
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                )
    }
    }

@Composable
fun CheckOrientation() {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    if(isPortrait){
        MainScreen()
    }
    else{
        LandscapeMainScreen()
    }


}




@Preview(
    showBackground = true
    )
@Composable
fun GreetingPreview() {
    ScreenShoutTheme {
        MainScreen()
    }
}



