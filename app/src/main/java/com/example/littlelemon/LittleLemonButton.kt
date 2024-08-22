package com.example.littlelemon

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.littlelemon.ui.theme.LLPrimary1
import com.example.littlelemon.ui.theme.LLPrimary2
import com.example.littlelemon.ui.theme.LLSecondary3
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun LittleLemonButton(buttonText: String, modifier: Modifier = Modifier, callback: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        onClick = callback,
        interactionSource = interactionSource,
        colors = (
            if (isPressed) {
                ButtonDefaults.buttonColors(
                    containerColor = LLPrimary1,
                    contentColor = LLSecondary3
                )
            }
            else {
                ButtonDefaults.buttonColors(
                    containerColor = LLPrimary2,
                    contentColor = Color.Black
                )
            }
        ),
        modifier = modifier
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = false)
@Composable
fun LittleLemonButtonPreview() {
    LittleLemonTheme {
        LittleLemonButton(buttonText = "Test Button") {}
    }
}