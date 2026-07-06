package com.tamara.expensetracker.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDots(){
    Card(
        modifier = Modifier
            .size(10.dp)
            .padding(),
        shape = CircleShape.copy(
            all = CornerSize(10.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(red = 255, green = 100, blue = 0)
        ),
    ){}
}

@Composable
fun ChipContainer(
    label: String,
    color: Color,
){
    Card(
        modifier = Modifier.padding(
            3.dp
        ).border(
            border = BorderStroke(
                .75.dp,
                color = color,
            ),
            shape = CircleShape.copy(
                CornerSize(16.dp)
            )
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = color.copy(
                alpha = .15f,
            )
        )
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
        )
    }
}