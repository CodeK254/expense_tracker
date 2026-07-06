package com.tamara.expensetracker.components

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamara.expensetracker.data.category.CategoryDataSource
import com.tamara.expensetracker.models.category.Category
import com.tamara.expensetracker.models.transaction.Transaction
import com.tamara.expensetracker.screens.bottomnav.HomeScreenViewModel
import com.tamara.expensetracker.utils.TransactionIconRegistry

@Composable
fun InputTabRow(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onSelect: (Int) -> Unit,
) {
    Card(
        shape = CircleShape.copy(
            CornerSize(
                12.dp
            )
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.Black.copy(
                    alpha = .65f
                ))
                .clip(
                    CircleShape.copy(
                        CornerSize(
                            12.dp
                        )
                    )
                ),
        ) {
            InputTabBar(
                modifier = Modifier.weight(1f)
                    .clickable{onSelect.invoke(0)},
                isSelected = selectedIndex == 0,
                label = "Income"
            )
            InputTabBar(
                modifier = Modifier.weight(1f)
                    .clickable{onSelect.invoke(1)},
                label = "Expense",
                isSelected = selectedIndex == 1,
            )
        }
    }
}

@Composable
fun InputTabBar(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    label: String,
) {
    Card(
        modifier = modifier.height(55.dp)
            .padding(4.dp),
        shape = CircleShape.copy(
            CornerSize(12.dp)
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = if (isSelected) Color(
                red = 255, green = 100, blue = 25
            ) else Color.Black
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.Black else Color.White,
            )
        }
    }
}

@Composable
fun AmountInputContainer(
    modifier: Modifier = Modifier,
    amount: String,
    isIncome: Boolean = false,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxSize()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = CircleShape.copy(
                    CornerSize(24.dp),
                )
            ),
        shape = CircleShape.copy(
            CornerSize(24.dp),
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Gray.copy(
                alpha = .15f
            )
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "AMOUNT",
                    fontSize = 16.sp,
                    color = Color.White.copy(
                        alpha = .75f
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = (if(isIncome) "+ " else "- ") + "KES",
                        fontSize = 16.sp,
                        color = Color.Yellow.copy(
                            alpha = .75f
                        ),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = amount,
                        fontSize = 25.sp,
                        color = Color.Gray.copy(
                            alpha = .75f
                        ),
                        modifier = Modifier.clickable{
                            onClick.invoke()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    active: Boolean = true,
    hint: String = "Type something...",
    maxLines: Int = 1,
    trailingIcon:  @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 15.sp,
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            modifier = Modifier.border(
                width = 1.dp,
                color = Color.Gray,
                shape = CircleShape.copy(
                    CornerSize(24.dp)
                )
            )
//                .height(50.dp)
                .fillMaxWidth(),
            value = value,
            enabled = active,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.White
            ),
            maxLines = maxLines,
            placeholder = {
                Text(
                    text = hint,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions().copy(
                capitalization = keyboardCapitalization,
                keyboardType = keyboardType,
            ),
            trailingIcon = trailingIcon,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorBorderColor = Color.Red.copy(
                    alpha = .65f,
                )
            ).copy(
                unfocusedContainerColor = Color.Gray.copy(
                    alpha = .15f
                ),
                errorContainerColor = Color.Gray.copy(
                    alpha = .15f
                ),
                focusedContainerColor = Color.Gray.copy(
                    alpha = .15f
                ),
                disabledContainerColor = Color.Gray.copy(
                    alpha = .15f
                ),
                focusedPlaceholderColor = Color.White,
                disabledPlaceholderColor = Color.White,
                cursorColor = Color.White,
            ),
            shape = CircleShape.copy(
                CornerSize(24.dp)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(
    modifier: Modifier = Modifier,
    label: String,
    value: Category,
    categories: List<Category>,
    selectOption: (Category) -> Unit,
) {
    val categories = categories
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth()
            .background(color = Color.Black.copy(
                .75f
            )),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
    ) {
        InputTextField(
            label = label,
            value = value.label,
            active = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        expanded = true
                    }
                )
            },
            onValueChange = {}
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color.Black.copy(
                    alpha = .85f
                )),
            expanded = expanded,
            onDismissRequest = {
                expanded = false;
            },
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = Color.Black.copy(
                            alpha = .75f
                        )),
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = TransactionIconRegistry.getVector(
                                        category.icon,
                                    ),
                                    contentDescription = "Transaction Icon",
                                    modifier = Modifier.size(40.dp).padding(
                                        all = 8.dp
                                    ),
                                    tint = Color(category.color)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = category.label,
                                color = Color.White
                            )
                        }
                    },
                    onClick = {
                        selectOption.invoke(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SaveInputButton(
    modifier: Modifier = Modifier,
    active: Boolean = false,
    label: String,
    onSave: () -> Unit,
) {
    FilledTonalButton(
        onClick = {
            onSave.invoke()
        },
        colors = ButtonColors(
            containerColor = if (active) Color(
                red = 255, green = 100, blue = 25
            ) else Color.Gray.copy(
                alpha = .35f
            ),
            contentColor = Color.White.copy(
                alpha = .75f
            ),
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White.copy(
                alpha = .75f
            ),
        ),
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                vertical = 12.dp
            )
        )
    }
}