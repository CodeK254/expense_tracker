package com.tamara.expensetracker.screens.bottomnav.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamara.expensetracker.R
import com.tamara.expensetracker.components.ChipContainer
import com.tamara.expensetracker.components.LoadingDots

@Composable
fun HomeScreen() {
    return Column(
        modifier = Modifier
            .background(
                color = Color.Black,
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(100.dp)
                .background(
                    color = Color(red = 236, green = 88, blue = 0).copy(
                        alpha = .75f
                    )
                )
        )
        Spacer(modifier = Modifier.height(70.dp))
        Image(
            painter = painterResource(R.drawable.expensify),
            contentDescription = "Expense Logo"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Expensify",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "SMART MONEY TRACKING",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = Color.White.copy(
                    alpha = .75f
                )
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChipContainer(
                label = "Track",
                color = Color.Yellow,
            )
            Spacer(modifier = Modifier.width(12.dp))
            ChipContainer(
                label = "Analyse",
                color = Color.Green,
            )
            Spacer(modifier = Modifier.width(12.dp))
            ChipContainer(
                label = "Save",
                color = Color.Red,
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            LoadingDots()
            Spacer(modifier = Modifier.padding(5.dp))
            LoadingDots()
            Spacer(modifier = Modifier.padding(5.dp))
            LoadingDots()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Version ${stringResource(id = R.string.app_version)}",
            color = Color.Gray.copy(
                alpha = .75f
            )
        )
    }
}

