package com.example.screenshout

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.github.skydoves.colorpicker.compose.ColorPickerController

class SampleViewModel:ViewModel(){

//    private val _previewScreenColor = MutableStateFlow(Color.Green)
////    val previewScreenColor:StateFlow<Color> = _previewScreenColor

    var selectedColor:Color by mutableStateOf(Color.Red)


    fun changeSelectedColor(color: Color){
        selectedColor=color
        Log.d("Composable", "Change Color Accessed")

    }
    var filledText by mutableStateOf("")

    fun changeFilledText(text:String){
        filledText=text
    }


    var hexColor by mutableStateOf("")
    private set

    fun changeHexColor(hex:String){
        hexColor=hex
    }

    //var controller by mutableStateOf(ColorPickerController.State())

//    fun changeController(newController: ColorPickerController){
//        controller=newController
//   }




}