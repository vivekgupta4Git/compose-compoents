package com.ruviapps.composecomponents.util

import androidx.compose.ui.graphics.Color

/**
 *@author Vivek Gupta on 19-2-24
 */
fun Color.invertColor(): Color {
    val red = 1f - this.red
    val green = 1f - this.green
    val blue = 1f - this.blue
    return Color(red, green, blue,this.alpha,this.colorSpace)
}