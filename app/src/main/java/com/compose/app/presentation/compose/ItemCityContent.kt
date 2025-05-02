package com.compose.app.presentation.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.app.domain.model.City
import com.compose.app.domain.model.Coord


@Composable
fun CityListItem(modifier: Modifier = Modifier, city: City) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {

        Canvas(modifier
            .fillMaxHeight()
            .width(10.dp)) {
            drawLine(
                color = Color.LightGray,
                strokeWidth = 3.dp.toPx(),
                start = Offset(3.dp.toPx() / 2, 0f),
                end = Offset(3.dp.toPx() / 2, size.height),
            )
        }


        Card(
            modifier = modifier
                .fillMaxWidth().padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CountryInitialCircle(city.country)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "${city.name}, ${city.country}",
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "${city.coord.lon}, ${city.coord.lat}",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}


@Composable
fun CountryInitialCircle(initials: String) {
    Surface(
        shape = CircleShape,
        color = Color.LightGray,
        modifier = Modifier.size(64.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.subtitle1,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ListIndicator(initials: String) {

    Surface(
        modifier = Modifier.size(44.dp),
        shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = Color.LightGray),
        color = Color.White // Inner circle color
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.subtitle1,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }
    }

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
