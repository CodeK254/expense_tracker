package com.tamara.expensetracker.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.jetco.ui.charts.linegraph.LineGraph
import com.developerstring.jetco.ui.charts.linegraph.config.*
import com.tamara.expensetracker.models.category.Category
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
fun FilterRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = Color.Gray.copy(
                        alpha = .1f
                    )
                )
                .clip(
                    shape = CircleShape.copy(
                        CornerSize(8.dp)
                    )
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilterCard(
                label = "Monthly",
                width = 75,
                isSelected = true,
            )
            FilterCard(
                label = "Yearly",
                width = 75,
                isSelected = false,
            )
        }
        Row(
            modifier = Modifier
                .background(
                    color = Color.Gray.copy(
                        alpha = .1f
                    )
                )
                .clip(
                    shape = CircleShape.copy(
                        CornerSize(8.dp)
                    )
                ),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilterCard(
                label = "Area",
                width = 50,
                isSelected = true,
            )
            FilterCard(
                label = "Bar",
                width = 50,
                isSelected = false,
            )
        }
    }
}

@Composable
fun FilterCard(
    modifier: Modifier = Modifier,
    label: String,
    width: Int,
    isSelected: Boolean,
) {
    Card(
        modifier = modifier
            .width(width.dp)
            .height(30.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = if (isSelected) Color(red = 255, green = 100, blue = 50) else Color.Transparent
        ),
        shape = CircleShape.copy(
            CornerSize(8.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Transparent
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.Black else Color.White
            )
        }
    }
}

@Composable
fun DetailsCard(
    modifier: Modifier = Modifier,
    label: String,
    amount: String,
    textColor: Color,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Gray.copy(
                alpha = .15f
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = label,
                color = Color.White.copy(
                    alpha = .75f
                ),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = amount,
                color = textColor.copy(
                    alpha = .75f
                ),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun LiveUpdateChart(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    var chartData: Map<String, Float> =
        categories.filter {
            it.value != 0.0f
        }.associate { it.label to it.value }
    val timeLabels: List<String> =
        categories.filter {
            it.value != 0f
        }.map { it.label }

    LaunchedEffect(Unit) {
        var currentIndex = 5

        while (currentIndex < timeLabels.size) {
            delay(2000)
            val newValue = Random.nextFloat() * (30f - 20f) + 20f
            val entries = chartData.toMutableMap()
            entries[timeLabels[currentIndex]] = newValue

            if (entries.size > 5) {
                val sorted = entries.entries.sortedBy { timeLabels.indexOf(it.key) }
                chartData = sorted.takeLast(5).associate { it.key to it.value }
            } else {
                chartData = entries
            }

            currentIndex++
            if (currentIndex >= timeLabels.size) currentIndex = 5
        }
    }

    val liveOrange = Color(0xFFFF6B35)

    LineGraph(
        chartData = chartData,
        chartHeight = 220.dp,
        lineConfig = LineGraphLineConfig(
            lineColor = liveOrange,
            lineWidth = 3.dp,
            strokeCap = StrokeCap.Round,
            smoothCurve = true,
            curvature = 0.3f
        ),
        areaFillConfig = LineGraphAreaFillConfig(
            enabled = true,
            brush = Brush.verticalGradient(
                listOf(liveOrange.copy(alpha = 0.3f), Color.Transparent)
            )
        ),
        pointConfig = LineGraphPointConfig(
            enabled = true, radius = 6.dp,
            color = liveOrange, borderColor = Color.White, borderWidth = 2.dp
        ),
        liveUpdateConfig = LineGraphLiveUpdateConfig(
            enabled = true,
            blinkEnabled = true,
            blinkDurationMillis = 1000,
            blinkMinRadius = 8f,
            blinkMaxRadius = 20f,
            blinkColor = liveOrange,
            pathTransitionDurationMillis = 800
        ),
        yAxisConfig = LineGraphDefaults.yAxisConfig(
            axisScaleCount = 4,
            isAxisLineEnabled = true,
            isAxisScaleEnabled = true,
            textStyle = TextStyle(fontSize = 11.sp, color = Color.White)
        ),
        xAxisConfig = LineGraphDefaults.xAxisConfig(
            isAxisLineEnabled = true,
            isAxisScaleEnabled = true,
            textStyle = TextStyle(fontSize = 11.sp, color = Color.White)
        ),
        gridLineStyle = LineGraphGridLineStyle(
            color = Color(0xFFF3F4F6), strokeWidth = 1.dp
        ),
        animationConfig = LineGraphAnimationConfig(
            enabled = true, durationMillis = 800
        ),
        horizontalDrawPadding = 12.dp
    )
}

@Composable
fun CategoryBreakdown(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    income: Float = 0f
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            "Category Breakdown",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column() {
            categories.forEach {
                CategoryRow(
                    label = it.label,
                    value = if(income != 0f) (it.value / income) else 0f,
                    color = Color(it.color),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryRow(
    label: String,
    value: Float,
    color: Color,
) {
    Log.d("EXPENSE-APP", "CategoryRow: $label - Value: $value")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Slider(
            value = value,
            valueRange = 0f..1f,
            enabled = false,
            onValueChange = {},
            thumb = {
                Spacer(modifier = Modifier.size(0.dp).padding(0.dp))
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    enabled = false,
                    modifier = Modifier.height(10.dp), // Make track thicker if desired,
                    thumbTrackGapSize = 0.0.dp,
                    colors = SliderDefaults.colors().copy(
                        disabledActiveTrackColor = color,
                        disabledInactiveTrackColor = color.copy(
                            alpha = .1f
                        ),
                    )
                )
            },
            colors = SliderDefaults.colors().copy(
                disabledActiveTrackColor = color,
                disabledInactiveTrackColor = color.copy(
                    alpha = .1f
                ),
            ),
            modifier = Modifier
                .height(5.dp)
                .padding(
//                vertical = 4.dp
                )
                .weight(1f),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "%.2f".format(value * 100) + "%",
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}