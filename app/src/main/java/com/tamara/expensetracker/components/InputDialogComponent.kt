package com.tamara.expensetracker.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DialogWithTextField(
    value: String,
    showDialog: Boolean = false,
    onDismiss: () -> Unit,
    onUpdateInput: (String) -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user taps outside or presses back
                onDismiss.invoke()
            },
            title = {
                Text(text = "Enter Amount")
            },
            text = {
                // We wrap the text field in a Column so it handles layout properly
                Column {
                    InputTextField(
                        modifier = Modifier.padding(
                            top = 20.dp
                        ),
                        value = value,
                        label = "AMOUNT",
                        hint = "e.g. 1,000",
                        keyboardType = KeyboardType.Number,
                    ){
                        if (it.all { char -> char.isDigit() }){
                            onUpdateInput.invoke(it)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismiss.invoke()
                    }
                ) {
                    Text("Confirm")
                }
            },
//            dismissButton = {
//                TextButton(
//                    onClick = {
//                        showDialog = false // Just close it without saving
//                    }
//                ) {
//                    Text("Cancel")
//                }
//            }
        )
    }
}