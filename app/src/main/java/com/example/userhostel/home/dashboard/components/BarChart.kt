package com.example.userhostel.home.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarChart(
    data: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 220.dp)
        .padding(horizontal = 8.dp),
    barColor: Color = MaterialTheme.colorScheme.primary,
    labelColor: Color = Color.Black,
    valueColor: Color = Color.DarkGray
) {
    if (data.isEmpty()) return

    val last5Data = if (data.size > 5) data.takeLast(5) else data
    val maxValue = last5Data.maxOf { it.second }.takeIf { it > 0 } ?: 1

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        last5Data.forEach { (label, value) ->
            val barWidthRatio = value.toFloat() / maxValue
            val barWidth = (barWidthRatio * 200).dp // Max bar width

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Label
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = labelColor,
                    modifier = Modifier.width(80.dp)
                )

                // Bar
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(barWidth)
                        .background(color = barColor, shape = RoundedCornerShape(4.dp))
                )

                // Value
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = value.toString(),
                    fontSize = 12.sp,
                    color = valueColor
                )
            }
        }
    }
}
