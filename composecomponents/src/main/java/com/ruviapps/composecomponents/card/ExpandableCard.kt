package com.ruviapps.composecomponents.card

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * # ExpandableCard
 *
 * ## [ExpandableCard] is a composable that can be used to create an expandable card that can be expanded or collapsed on click of the header
 *
 * @param modifier Modifier
 * @param iconId Icon to be displayed on the left side of the card
 * @param dropDownIconId Icon to be displayed on the right side of the card
 * @param requiredCardHeight Height of the card
 * @param headingText Text to be displayed on the top of the card
 * @param headingInitialTextColor Starting Color of the text for the header
 * @param onExpandHeadingColor Color of the text when the card is expanded
 * @param headerInitialBackgroundColor Color of the card when it is not expanded
 * @param headerBackgroundColorOnExpand Color of the card when it is expanded
 * @param enableBorder Enable border on the card
 * @param borderColor Color of the border when enabled
 * @param headerTextStyle Style of the text for the header
 * @param valueText Text to be displayed on the right side of the card
 * @param valueTextColor Color of the text for the value
 * @param valueTextStyle Style of the text for the value
 * @param shape Shape of the card
 * @param expandState State of the card when it is expanded or collapsed
 * @param onClick Function to be called when the card is clicked
 * @param content Content of the card
 */

@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int?,
    dropDownIconId: Painter = painterResource(android.R.drawable.arrow_down_float),
    requiredCardHeight: Dp = 73.dp,
    headingText: String,
    headingInitialTextColor: Color = Color.Black,
    headerPaddingValues: PaddingValues,
    onExpandHeadingColor: Color = Color.White,
    headerInitialBackgroundColor: Color = Color.White,
    headerBackgroundColorOnExpand: Color = Color.Black,
    enableBorder: Boolean = true,
    borderColor: Color = Color.Black,
    headerTextStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    valueText: String = "",
    valueTextColor: Color = Color.Black,
    valueTextStyle: TextStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
    shape: Shape = RoundedCornerShape(16.dp),
    expandState: Boolean,
    onClick: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    //animating color and angle
    val backGroundColor by animateColorAsState(
        targetValue =
        if (expandState)
            headerBackgroundColorOnExpand
        else
            headerInitialBackgroundColor, tween(200), label = "background"
    )
    val angle by animateFloatAsState(
        targetValue = if (expandState) 180f else 0f,
        tween(700), label = "angle"
    )
    //animating text
    val headingTextColor by animateColorAsState(
        targetValue =
        if (expandState)
            onExpandHeadingColor
        else
            headingInitialTextColor, label = "headingTextColor"
    )
    //animating tint of icon drawable put on the right side of the card
    val tint by animateColorAsState(
        targetValue = if (expandState) Color.White else Color.Black,
        tween(700), label = "tint"
    )
    //value text color change animation
    val valueTextColorTint by animateColorAsState(
        targetValue = if (expandState) Color.White else valueTextColor,
        tween(700), label = "valueTextColorTint"
    )
    //creating the surface of the card
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .then(
                if (enableBorder)
                    Modifier.border(width = 1.dp, shape = shape, color = borderColor)
                else
                    Modifier
            )
            .clickable {
                onClick(expandState)
            }
            .animateContentSize(
                spring(
                    stiffness = Spring.StiffnessMediumLow,
                    dampingRatio = Spring.DampingRatioMediumBouncy
                )
            ),
        shape = shape,
    ) {
        //this outer column is necessary
        Column {
            Column {
                //header i.e card
                Row(
                    modifier = Modifier
                        .requiredHeight(requiredCardHeight)
                        .fillMaxWidth()
                        .background(backGroundColor)
                        .padding(headerPaddingValues),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (iconId != null)
                        Icon(
                            imageVector = ImageVector.vectorResource(id = iconId),
                            contentDescription = "$headingText icon",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .requiredSize(40.dp)
                                .aspectRatio(1f)
                        )
                    Text(
                        text = headingText,
                        modifier = Modifier.weight(1f),
                        style = headerTextStyle,
                        color = headingTextColor
                    )

                    if (valueText.isNotBlank())
                        Text(
                            text = valueText,
                            style = valueTextStyle,
                            color = valueTextColorTint,
                            modifier = Modifier.padding(2.dp)
                        )

                    Icon(painter = dropDownIconId, contentDescription = headingText,
                        tint = tint,
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                onClick(expandState)
                            }
                            .rotate(
                                angle
                            )

                    )
                }
            }
            //dynamic content appears as an element of column
            androidx.compose.animation.AnimatedVisibility(
                visible = expandState,
                enter = expandVertically(),
                exit = shrinkVertically()
            )
            {
                content()
            }
        }


    }


}


@Preview
@Composable
@PreviewScreenSizes
fun PreviewExpandableCard() {
    var expandState by remember {
        mutableStateOf(false)
    }
    ExpandableCard(
        iconId = null,
        headingText = "Click to Expand anywhere on Header",
        valueText = "",
        headerPaddingValues = PaddingValues(20.dp),
        expandState = expandState,
        headerBackgroundColorOnExpand = colorResource(id = android.R.color.holo_orange_dark),
        onClick = {
            expandState = !it
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = "Expanded State")
        }
    }
}



