package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(includeProfile: Boolean, navController: NavHostController? = null) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Little Lemon Logo",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxHeight(0.8f),
                )
            }
        },
        actions = {
            if (includeProfile) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .fillMaxHeight(0.95f)
                            .clickable { navController?.navigate(Profile.route) }
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    )
}

@Preview(showBackground = false)
@Composable
fun TopBarPreview() {
    LittleLemonTheme {
        TopBar(includeProfile = true)
    }
}