package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LLPrimary1
import com.example.littlelemon.ui.theme.LLPrimary2
import com.example.littlelemon.ui.theme.LLSecondary3
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Hero(modifier: Modifier = Modifier, searchField: (@Composable () -> Unit)? = null) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = LLPrimary1)
            .padding(5.dp)
    ) {
        Text(
            text = "Little Lemon",
            style = MaterialTheme.typography.titleLarge,
            color = LLPrimary2,
            modifier = Modifier.padding(5.dp)
        )

        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.65f)
            ) {
                Text(
                    text = "Chicago",
                    style = MaterialTheme.typography.labelLarge,
                    color = LLSecondary3
                )
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    style = MaterialTheme.typography.headlineMedium,
                    color = LLSecondary3
                )
            }

            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        if (searchField != null) {
            searchField()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroPreview() {
    LittleLemonTheme {
        Hero()
    }
}