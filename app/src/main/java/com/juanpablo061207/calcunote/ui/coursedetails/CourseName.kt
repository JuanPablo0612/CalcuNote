package com.juanpablo061207.calcunote.ui.coursedetails

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CourseName(courseName: String, onEditClicked: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = courseName,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onEditClicked) {
            Icon(Icons.Filled.Edit, contentDescription = null)
        }
    }
}