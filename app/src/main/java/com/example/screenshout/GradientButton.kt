package com.example.screenshout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.screenshout.ui.theme.ScreenShoutTheme


@Composable
fun GradientButton(viewModel: SampleViewModel) {
    val context = LocalContext.current
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFF1A1A8A), Color(0xFF3030BE)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )
    val gradient2 = Brush.linearGradient(
        colors = listOf(Color(0xFF5252BD), Color(0xFFBEBEFF)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )


    // Applying the gradient to a Box that wraps the Button
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(55.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(brush = gradient)
            .padding(5.dp)
    ) {
        // Button with transparent background
        Button(
            onClick = {
                val intent = Intent(context, NameActivity::class.java)
                intent.putExtra("name", viewModel.filledText)
                intent.putExtra("hexColor", viewModel.hexColor)

                //share the hexColor using Shared Preferences
                val sharedPref: SharedPreferences =
                    context.getSharedPreferences("Colors", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("hexColor", viewModel.hexColor)  // Assuming hexColor is from viewModel
                editor.apply()

                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxSize()

            ,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)  // Make the Button background transparent
        ) {
            Text(text = "SHOUT NAME", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }
    }
}

@Preview
@Composable
fun PreviewGradientButton() {
    ScreenShoutTheme {
        GradientButton(viewModel = SampleViewModel())
    }


}