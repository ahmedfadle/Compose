package com.compose.app.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.app.R
import com.compose.app.presentation.viewmodel.CitiesViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CityScreen(viewModel: CitiesViewModel) {
    val state = viewModel.citiesState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchCitiesOnce()
    }

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 10.dp),
                text = stringResource(R.string.city_search),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

        },
        bottomBar = {
            CitySearchScreen(
                Modifier,
                viewModel
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.cities, state.value.noOfCities ?: 0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )

                when {
                    state.value.isLoading -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            alignment = Alignment.CenterHorizontally
                        )
                    )

                    state.value.uiCities.isNullOrEmpty() ->
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
                            state.value.uiCities?.forEach { (key, cities) ->
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
}





