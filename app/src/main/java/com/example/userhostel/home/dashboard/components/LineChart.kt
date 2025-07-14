package com.example.userhostel.home.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun LineChart(
    data: Map<String, Int>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .padding(8.dp)
) {
    val points = data.values.mapIndexed { index, value -> index to value }
    val labels = data.keys.toList()

    Canvas(modifier = modifier) {
        val maxY = (data.values.maxOrNull() ?: 100).toFloat()
        val spacing = size.width / (points.size.coerceAtLeast(1))
        val scaleY = size.height / maxY

        // Draw lines between points
        for (i in 0 until points.size - 1) {
            val x1 = i * spacing
            val y1 = size.height - points[i].second * scaleY
            val x2 = (i + 1) * spacing
            val y2 = size.height - points[i + 1].second * scaleY

            drawLine(
                color = Color.Blue,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 4f
            )
        }

        // Draw points + value labels
        points.forEachIndexed { index, point ->
            val x = index * spacing
            val y = size.height - point.second * scaleY

            // Dot
            drawCircle(Color.Red, radius = 6f, center = Offset(x, y))

            // Price label above
            drawContext.canvas.nativeCanvas.drawText(
                "${point.second}",
                x,
                y - 10f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 30f
                }
            )

            // Date label below
            drawContext.canvas.nativeCanvas.drawText(
                labels.getOrNull(index) ?: "",
                x,
                size.height + 30f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 26f
                }
            )
        }
    }
}
