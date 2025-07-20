package com.example.batteryindicator.batteryindicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.batteryindicator.R
import com.example.batteryindicator.ui.theme.BatteryIndicatorTheme
import kotlin.concurrent.timer

@Composable
fun BatteryIndicator(modifier: Modifier = Modifier) {

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(20.dp)
        .height(60.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.heart),
            tint = MaterialTheme.colorScheme.onError,
            contentDescription = null
        )

        TabIndicatorScope(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.clover),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )


    }


}

@Composable
fun TabIndicatorScope(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxHeight()) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val indicatorPath = Path().apply {
                moveTo(width * 0.1f, height * 0.1f)
                lineTo(width * 0.9f, height * 0.1f)
                lineTo(width * 0.9f, height * 0.9f)
                lineTo(width * 0.1f, height * 0.9f)
                lineTo(width * 0.1f, height * 0.1f)



                close()

            }

            var x = width * 0.1f

            repeat(8) {
                drawRect(
                    color =Color.Black,
                    style = Fill,

                    topLeft = Offset(x = x, y = height * 0.1f),
                    size = Size(width * 0.1f, height * 0.8f)
                )
                x += width * 0.1f
            }



            drawPath(
                indicatorPath,
                style = Stroke(width = 2f),
                color = Color.Black
            )


        }
    }

}