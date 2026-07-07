package com.tamara.expensetracker.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.developerstring.jetco.ui.charts.barchart.ColumnBarChart
import com.developerstring.jetco.ui.charts.barchart.config.BarChartConfig
import com.developerstring.jetco.ui.charts.barchart.config.BarChartDefaults
import com.developerstring.jetco.ui.charts.linegraph.LineGraph
import com.developerstring.jetco.ui.charts.linegraph.config.*
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.models.transaction.Transaction


@Composable
fun FilterRow(
    modifier: Modifier = Modifier,
    isBar: Boolean = false,
    isMonthly: Boolean = true,
    onMonthly: () -> Unit = {},
    onYearly: () -> Unit = {},
    onArea: () -> Unit = {},
    onBar: () -> Unit = {},
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
                isSelected = isMonthly,
                onToggle = onMonthly
            )
            FilterCard(
                label = "Yearly",
                width = 75,
                isSelected = !isMonthly,
                onToggle = onYearly
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
                isSelected = !isBar,
                onToggle = onArea
            )
            FilterCard(
                label = "Bar",
                width = 50,
                isSelected = isBar,
                onToggle = onBar
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
    onToggle: () -> Unit,
) {
    Card(
        modifier = modifier
            .width(width.dp)
            .height(30.dp)
            .clickable {
                onToggle.invoke()
            },
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
    isBarGraph: Boolean = false,
    isMonthly: Boolean = true,
    transactions: List<Transaction>,
    onCalculate: (transaction: Transaction) -> Int,
) {
    var chartData: MutableMap<String, Float>
    if (!isMonthly) {
        chartData = mutableMapOf(
            "January" to 0f,
            "February" to 0f,
            "March" to 0f,
            "April" to 0f,
            "May" to 0f,
            "June" to 0f,
            "July" to 0f,
            "August" to 0f,
            "September" to 0f,
            "October" to 0f,
            "November" to 0f,
            "December" to 0f
        )
    } else {
        chartData = mutableMapOf(
            "Week1" to 0f,
            "Week2" to 0f,
            "Week3" to 0f,
            "Week4" to 0f
        )
    }
    for (transaction in transactions){
        if(isMonthly) {
            Log.d("EXPENSE-APP", "LiveUpdateChart: THIS IS MONTHLY")
            when (onCalculate.invoke(transaction)) {
                1 -> chartData["Week1"] = chartData["Week1"]?.plus(transaction.amount) as Float
                2 -> chartData["Week2"] = chartData["Week2"]?.plus(transaction.amount) as Float
                3 -> chartData["Week3"] = chartData["Week3"]?.plus(transaction.amount) as Float
                4 -> chartData["Week4"] = chartData["Week4"]?.plus(transaction.amount) as Float
            }
        } else {
            Log.d("EXPENSE-APP", "LiveUpdateChart: THIS IS YEARLY")
            when (onCalculate.invoke(transaction)) {
                1 -> chartData["January"] = chartData["January"]?.plus(transaction.amount) as Float
                2 -> chartData["February"] = chartData["February"]?.plus(transaction.amount) as Float
                3 -> chartData["March"] = chartData["March"]?.plus(transaction.amount) as Float
                4 -> chartData["April"] = chartData["April"]?.plus(transaction.amount) as Float
                5 -> chartData["May"] = chartData["May"]?.plus(transaction.amount) as Float
                6 -> chartData["June"] = chartData["June"]?.plus(transaction.amount) as Float
                7 -> chartData["July"] = chartData["July"]?.plus(transaction.amount) as Float
                8 -> chartData["August"] = chartData["August"]?.plus(transaction.amount) as Float
                9 -> chartData["September"] = chartData["September"]?.plus(transaction.amount) as Float
                10 -> chartData["October"] = chartData["October"]?.plus(transaction.amount) as Float
                11 -> chartData["November"] = chartData["November"]?.plus(transaction.amount) as Float
                12 -> chartData["December"] = chartData["December"]?.plus(transaction.amount) as Float
            }
            chartData.forEach {
                string, f ->
                Log.d("EXPENSE-APP", "Transaction => ${transaction.createdAt} - ChartData $string: $f")
            }
        }
    }

    val keys = chartData.keys.toList()

    if(isMonthly) {
        for (key in keys) {
            if (chartData[key] == 0f) {
                Log.d("EXPENSE-APP", "REMOVING: $key")
                chartData.remove(key)
            } else {
                try {
                    Log.d(
                        "EXPENSE-APP",
                        "SKIPPING: $key - CHECKING ${chartData[keys[keys.indexOf(key) - 1]]}"
                    )
                    if (keys.indexOf(key) > 0 && chartData[keys[keys.indexOf(key) - 1]] == null) {
                        Log.d("EXPENSE-APP", "REASSIGNING: $key")
                        chartData[keys[keys.indexOf(key) - 1]] = 0f
                    }
                } catch (e: Exception) {
                    Log.d("EXPENSE-APP", "LiveUpdateChart: $e")
                }
            }
        }

        chartData = chartData.toSortedMap()
    }

    val liveOrange = Color(0xFFFF6B35)

    if(isBarGraph){
        ColumnBarChart(
            modifier = modifier,
            chartData = chartData,
            barChartConfig = BarChartDefaults.columnBarChartConfig(
                color = liveOrange,
                width = 40.dp,
                shape = CircleShape.copy(
                    topEnd = CornerSize(12.dp),
                    topStart = CornerSize(12.dp),
                    bottomEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                ),
                height = 220.dp,
            ),
            yAxisConfig = BarChartDefaults.yAxisConfig(
                axisScaleCount = 4,
                isAxisLineEnabled = true,
                isAxisScaleEnabled = true,
                textStyle = TextStyle(fontSize = 11.sp, color = Color.White)
            ),
            xAxisConfig = BarChartDefaults.xAxisConfig(
                isAxisLineEnabled = true,
                isAxisScaleEnabled = true,
                textStyle = TextStyle(fontSize = 11.sp, color = Color.White)
            ),
        )
    } else {
        LineGraph(
            modifier = modifier,
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
        Column {
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
                Spacer(modifier = Modifier
                    .size(0.dp)
                    .padding(0.dp))
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