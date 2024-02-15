package com.ruviapps.composecomponents.buttons.loadingbuttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * # LoadingButton
 * A Loading Button which enable develoers to build an button which gets disabled
 * when clicked and its content will be replaced by a circular indicator until the processing
 * is done thereafter it will re-activate after the state changes.
 * ### This is just a wrapper above Material3 Button.
 * @param onClick called when this button is clicked
 * @param isLoading mark composable to loading state when true otherwise no loading state
 * @param modifier the [Modifier] to be applied to this button
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.buttonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonElevation.shadowElevation] and [ButtonElevation.tonalElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 * */
@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLoading : Boolean = false,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = {
            if(!isLoading)
                onClick()
        },
        modifier = modifier,
        enabled = !isLoading,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = {
            if (isLoading)
                CircularProgressIndicator()
            else
                content()
        }
    )
}

@Preview
@Composable
fun PreviewLoadingButton() {
    var state by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    LoadingButton(isLoading = state, onClick = {
        scope.launch {
            state = true
            delay(5000)
            state = false
        }
    }) {
        Text("Click to Load")
    }
}
