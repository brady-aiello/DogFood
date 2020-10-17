package com.example.dogfood

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberScrollableController
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ConfigurationAmbient
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import kotlin.math.roundToInt

@Preview
@Composable
fun Screen() {
    var offset by remember { mutableStateOf(0f) }
    val ctrlr = rememberScrollableController{
        offset += it
        it
    }
    Row(
        Modifier
            .background(Color.Black)
            .fillMaxSize()
            .scrollable(
                    Orientation.Horizontal,
                    ctrlr
            )) {
        Row(Modifier.offset(getX = {offset}, getY = {0f})) {
            MoviePoster()
            MoviePoster()
            MoviePoster()
        }


    }
}

@Composable
private fun MoviePoster(modifier: Modifier = Modifier) {
    val screenSize = ConfigurationAmbient.current.screenWidthDp * .75f
    Column(modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .width(screenSize.dp)
            .background(Color.White)
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoilImage(
            data = "https://i.etsystatic.com/15963200/r/il/25182b/2045311689/il_570xN.2045311689_7m2o.jpg",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(180.dp)
                .aspectRatio(0.674f)
                .clip(RoundedCornerShape(10.dp))
        )
        Text("Joker",
            fontSize = 24.sp,
            color = Color.Black
        )
        Row {
            Chip("Action")
            Chip("Drama")
            Chip("History")
        }
        StarRating(9.0f)
        Spacer(Modifier.height(30.dp))
        //MyImageButton()
        BuyTicketButton{}
    }
}

@Composable fun BuyTicketButton(onClick: () -> Unit) {
    Button(onClick = onClick,
            backgroundColor = Color.DarkGray,
            elevation = 0.dp,
            modifier = Modifier
                    .fillMaxWidth()
    ) {
        Text("Buy Ticket", color = Color.White)
    }
}

@Composable fun StarRating(rating: Float) {

}


@Composable fun Chip(label: String, modifier: Modifier = Modifier) {
    Text(
            label,
            fontSize = 9.sp,
            color = Color.Gray,
            modifier = modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(50))
                    .padding(horizontal = 10.dp, vertical = 2.dp)
    )

}

@Composable fun MyImageButton() {
    Button(onClick = { }, modifier = Modifier.wrapContentSize()) {
        CoilImage(
                data = "https://i.pcmag.com/imagery/articles/02G9RJiXPu5zukHyghdjbG2-2.v_1569470712.jpg",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(32.dp)
        )
        Text(text = "Andy", modifier = Modifier.padding(start = 10.dp))
    }
}

@Composable
fun Modifier.offset(
    getX: () -> Float,
    getY: () -> Float,
    rtlAware: Boolean = true
) = this then object : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureScope.MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            if (rtlAware) {
                placeable.placeRelative(getX().roundToInt(), getY().roundToInt())
            } else {
                placeable.place(getX().roundToInt(), getY().roundToInt())
            }
        }
    }
}