package com.example.screenshout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatShapes
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.screenshout.ui.theme.ScreenShoutTheme
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val context = LocalContext.current
    Log.d("Composable", "HomePage recomposed")


    val viewModel= viewModel<SampleViewModel>()
    val hexColor = viewModel.hexColor
    val filledText = viewModel.filledText
    val selectedColor=viewModel.selectedColor
    val controller = rememberColorPickerController()

    var columnHeight by remember { mutableStateOf(0) }
    var columnWidth by remember{ mutableStateOf(0) }
    val density= LocalDensity.current

    val chosenColor:Color= remember {
        viewModel.selectedColor
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        //.padding(start=10.dp,end=10.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(10.dp)
            ,
            value = viewModel.filledText,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = Color.White
            ),
            textStyle = TextStyle(
                Color.White,
                fontSize = 18.sp,
            ),
            label = { Text(text = "Name",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ) },
            onValueChange = {
                if (it.length > 10) {
                    Toast.makeText(
                        context, "Name should be less than 10 characters", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.changeFilledText(it)
                }
            },
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {

                    Icon(
                        imageVector = Icons.Filled.FormatShapes,
                        contentDescription = "Person Icon",
                        tint = Color.White
                    )

                }

            },
            placeholder = {
                Text(text = "Enter Name:",color=Color.LightGray)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(
                onGo = {
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

                }
            )

            )

        Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .border(2.dp,Color.Black, RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
                    .background(selectedColor)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {


                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned { coordinates ->
                                columnHeight = coordinates.size.height
                                columnWidth-coordinates.size.width
                            }
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        if(columnHeight>0){

                            val availableHeightInDp=with(density){columnHeight.toDp()}
                            val fontSize=(availableHeightInDp.value / (filledText.length*1.465)).sp

                            val textList = filledText.toCharArray().map { it.toString() }

                             textList.forEach {
                            Text(
                                text = it,
                                modifier = Modifier.padding(1.dp),
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontSize = fontSize,
                                fontWeight = FontWeight.Bold

                            )
                            }
                        }

                    }


                }
            }


        Spacer(modifier = Modifier.height(20.dp))


        HsvColorPicker(
            initialColor=chosenColor,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(250.dp)
                .padding(10.dp)
            ,
            controller = controller,
            onColorChanged = {

                viewModel.changeSelectedColor(it.color)
                viewModel.changeHexColor(it.hexCode)

            },

            )
        
        Spacer(modifier = Modifier.height(20.dp))

        Spacer(modifier = Modifier.height(15.dp))

        GradientButton(viewModel = viewModel)


    }

}

@Preview
@Composable
fun MainScreenPreview() {

    ScreenShoutTheme {
        MainScreen()
    }
    
}
