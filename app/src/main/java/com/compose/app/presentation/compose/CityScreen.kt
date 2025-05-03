package com.compose.app.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.app.R
import com.compose.app.presentation.viewmodel.CitiesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CityScreen(
    viewModel: CitiesViewModel = hiltViewModel(),
    insets: PaddingValues = WindowInsets.ime.asPaddingValues()
) {

    val state = viewModel.citiesState.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.fetchCitiesOnce()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(top = 10.dp),
                        text = stringResource(R.string.city_search),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                },
            )
        },
        bottomBar = {
                SearchField(
                    Modifier,
                    viewModel
                )

        },
        modifier = Modifier
            .padding(insets)
    ) { innerPadding ->


        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding).padding(start = 8.dp , end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.cities, state.value.noOfCities ),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )

            when {
                state.value.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                )

                state.value.uiCities.isEmpty() ->
                    Text(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.no_data_found),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )

                state.value.error != null ->
                    Text(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Error: ${state.value.error}"
                    )

                else ->
                    LazyColumn {
                        state.value.uiCities.forEach { (key, cities) ->
                            item {
                                ListIndicator(key.uppercase())
                            }
                            items(cities.toMutableList()) { city ->
                                CityListItem(modifier = Modifier.padding(start = 20.dp), city)
                            }

                        }

                    }
            }
        }

    }
}
