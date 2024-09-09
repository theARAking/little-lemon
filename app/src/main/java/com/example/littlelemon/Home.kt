package com.example.littlelemon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.ui.theme.LLPrimary1
import com.example.littlelemon.ui.theme.LLSecondary3
import com.example.littlelemon.ui.theme.LLSecondary4
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.Job

@Composable
fun Home(
    navController: NavHostController?,
    database: MenuDatabase?,
    updateMenuDatabase: (() -> Job)?,
    modifier: Modifier = Modifier
) {
    updateMenuDatabase?.invoke()

    val menuItems = database?.menuDao()?.getMenuItems()?.observeAsState(emptyList())?.value

    var searchPhrase by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {TopBar(includeProfile = true, navController)},
        modifier = modifier.fillMaxSize()
    ) {
        innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                val searchField = @Composable {
                    TextField(
                        value = searchPhrase,
                        onValueChange = { searchPhrase = it },
                        placeholder = { Text(text = "Enter Search Phrase") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Hero(searchField = searchField)

                Text(
                    text = "ORDER FOR DELIVERY!",
                    textAlign = TextAlign.Left,
                    color = LLSecondary4,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                var categoryFilter by remember {
                    mutableStateOf("")
                }
                val setCategoryFilter = { filter: String ->
                    categoryFilter = filter
                }
                val getCategoryFilter = {
                    categoryFilter
                }

                CategoryFilters(
                    menuItems = menuItems,
                    setCategoryFilter = setCategoryFilter,
                    getCategoryFilter = getCategoryFilter
                )

                MenuItems(
                    menuItems = menuItems?.filter {
                        (if (!searchPhrase.isBlank()) it.title.contains(searchPhrase, true) else true) && (if (categoryFilter != "") it.category == categoryFilter else true)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryFilters(
    menuItems: List<MenuItem>?,
    setCategoryFilter: (String) -> Unit,
    getCategoryFilter: () -> String
) {
    if (menuItems != null) {
        val categories: MutableSet<String> = mutableSetOf()
        for (item: MenuItem in menuItems) {
            categories.add(item.category)
        }

        if (categories.size > 0) {
            LazyRow(
                modifier = Modifier.padding(5.dp)
            ) {
                items(categories.toList()) { category ->
                    Button(
                        shape = RoundedCornerShape(16.dp),
                        colors = if (getCategoryFilter() == category) ButtonDefaults.buttonColors(LLPrimary1) else ButtonDefaults.buttonColors(LLSecondary3),
                        onClick = {
                            if (getCategoryFilter() == category) {
                                setCategoryFilter("")
                            }
                            else {
                                setCategoryFilter(category)
                            }
                        },
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    ) {
                        Text(
                            text = category[0].uppercaseChar() + category.slice(1..<category.length),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (getCategoryFilter() == category) LLSecondary3 else LLPrimary1
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItems(menuItems: List<MenuItem>?, modifier: Modifier = Modifier) {
    if (menuItems?.isEmpty() == true) {
        Text(
            text = "There are no menu items.",
            textAlign = TextAlign.Center,
            color = LLSecondary3,
            style = MaterialTheme.typography.titleSmall
        )
    }
    else {
        LazyColumn( // Problem with Height
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            itemsIndexed(menuItems!!) { index, menuItem ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = menuItem.title,
                            color = LLSecondary4,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = menuItem.description,
                            color = LLPrimary1,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(top = 5.dp, bottom = 5.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "$${menuItem.price}",
                            color = LLPrimary1,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    GlideImage(
                        model = menuItem.image,
                        contentDescription = menuItem.title,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxWidth(0.2f)
                    )
                }

                if (index < menuItems.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Home(
            navController = null,
            database = null,
            updateMenuDatabase = null
        )
    }
}