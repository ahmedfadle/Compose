package com.compose.app.presentation.compose

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.app.common.utilits.countryCodeToEmoji
import com.compose.app.common.utilits.startNavigateToGoogleMaps
import com.compose.app.domain.model.City
import com.compose.app.domain.model.Coord
import com.compose.app.ui.theme.LocalCornerRadius
import com.compose.app.ui.theme.LocalSmallSpaces
import com.fawry.fawryb2b.core.design_system.theme.LocalSizes


@Composable
fun CityListItem(
    modifier: Modifier = Modifier,
    city: City,
    context: Context = LocalContext.current,
    isLastItem: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {

        Box(
            modifier = Modifier
                .width(40.dp)
                .padding(start = 3.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            VerticalDivider(
                modifier = Modifier,
                thickness = 1.dp,
                color = Color.LightGray
            )

            if (isLastItem)
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
        }

        Card (
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(end = 8.dp, bottom = 10.dp),
            shape = RoundedCornerShape(LocalCornerRadius.current.extraLarge),
            elevation = CardDefaults.cardElevation(4.dp),
            onClick = {
                context.startNavigateToGoogleMaps(city.coord.lat,city.coord.lon)
            },
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CountryInitialCircle(city.country)

                Spacer(modifier = Modifier.width(LocalSmallSpaces.current.small))

                Column {
                    Text(
                        text = "${city.name}, ${city.country}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )

                    Text(
                        text = "${city.coord.lon}, ${city.coord.lat}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}


@Composable
fun CountryInitialCircle(initials: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(LocalSizes.current.substantial)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        CountryFlagEmoji(initials)
    }
}

@Composable
fun ListIndicator(initials: String) {

    Surface(
        modifier = Modifier.size(LocalSizes.current.large),
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        color = Color.White
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun CountryFlagEmoji(countryCode: String) {
    val flag = remember (countryCode) {
        countryCode.countryCodeToEmoji()
    }
    Text(
        text = flag,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}


@Preview(showBackground = true)
@Composable
fun CountryInitialCirclePreview() {
    CountryInitialCircle("CA")
}

@Preview(showBackground = true)
@Composable
fun CountryInitialCircle1Preview() {
    ListIndicator("CA")
}


@Preview(showBackground = true)
@Composable
fun CityListItemPreview() {
    CityListItem(Modifier, City(5454545, Coord(45554544.0, 54545.0), "EG", "Cairo"))
}
