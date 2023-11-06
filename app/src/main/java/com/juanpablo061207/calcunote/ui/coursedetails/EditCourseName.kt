package com.juanpablo061207.calcunote.ui.coursedetails

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.juanpablo061207.calcunote.R

@Composable
fun EditCourseName(
    courseName: String,
    isValidCourseName: Boolean,
    onUpdate: (newName: String) -> Unit,
    onSave: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = courseName,
            onValueChange = { text ->
                onUpdate(text)
            },
            label = { Text(text = stringResource(id = R.string.course_name_field)) },
            isError = !isValidCourseName,
            supportingText = {
                if (!isValidCourseName) {
                    Text(text = stringResource(id = R.string.course_name_error))
                }
            },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            enabled = isValidCourseName,
            onClick = { onSave() }
        ) {
            Icon(Icons.Filled.Done, contentDescription = null)
        }
    }
}