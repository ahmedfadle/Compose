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
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.compose.app.ui.theme.LocalSmallSpaces
import com.fawry.fawryb2b.core.design_system.theme.LocalSizes


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
                            .height(LocalSizes.current.large)
                            .padding(top = LocalSmallSpaces.current.small),
                        text = stringResource(R.string.city_search),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                },
            )
        },
        bottomBar = {
                SearchField(Modifier, viewModel)

        },
        modifier = Modifier
            .padding(insets)
    ) { innerPadding ->


        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding).padding(start = LocalSmallSpaces.current.small , end = LocalSmallSpaces.current.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .padding(LocalSmallSpaces.current.extraMedium, bottom = LocalSmallSpaces.current.extraMedium)
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
                            .padding(LocalSmallSpaces.current.extraMedium, bottom = LocalSmallSpaces.current.extraMedium)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.no_data_found),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )

                state.value.error != null ->
                    Text(
                        modifier = Modifier
                            .padding(top = LocalSmallSpaces.current.extraMedium, bottom = LocalSmallSpaces.current.extraMedium)
                            .align(Alignment.CenterHorizontally),
                        text = "Error: ${state.value.error}"
                    )

                else ->
                    LazyColumn {
                        state.value.uiCities.forEach { (key, cities) ->
                            item {
                                ListIndicator(key.uppercase())
                            }
                            itemsIndexed(cities.toMutableList()) {index , city ->
                                CityListItem(modifier = Modifier.padding(start = LocalSmallSpaces.current.extraMedium),
                                    city = city, isLastItem = index == state.value.noOfCities -1
                                )
                            }

                        }

                    }
            }
        }

    }
}
