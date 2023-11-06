package com.juanpablo061207.calcunote.ui.coursedetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanpablo061207.calcunote.R
import com.juanpablo061207.calcunote.domain.model.Note
import com.juanpablo061207.calcunote.ui.common.getNoteColor

@Composable
fun NoteItem(
    note: Note,
    onUpdate: (Note) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var noteNameText by remember { mutableStateOf(note.noteName) }
    var noteValue: Double? by remember { mutableStateOf(note.note) }
    var noteText by remember { mutableStateOf(note.note.toString()) }
    var isValidNote by remember { mutableStateOf(true) }
    var percentageValue: Double? by remember { mutableStateOf(note.percentage) }
    var percentageText by remember { mutableStateOf(note.percentage?.toString() ?: "") }
    var changed by remember { mutableStateOf(false) }
    val visibleState = remember { MutableTransitionState(false) }
    var deleted by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        visibleState.targetState = true
    }

    LaunchedEffect(noteNameText, noteText, percentageText, note) {
        val changedNoteName = noteNameText != note.noteName
        val changedNote = noteValue != note.note
        val changedPercentage = percentageValue != note.percentage

        changed = changedNoteName || changedNote || changedPercentage
    }

    LaunchedEffect(key1 = visibleState.currentState) {
        if (!visibleState.currentState && deleted) {
            onDelete()
        }
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = expandHorizontally(),
        exit = shrinkHorizontally()
    ) {
        OutlinedCard(
            border = BorderStroke(2.dp, color = getNoteColor(note.note)),
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .weight(1f)
                        .animateContentSize()
                ) {
                    OutlinedTextField(
                        value = noteNameText,
                        onValueChange = { newValue ->
                            noteNameText = newValue
                        },
                        label = {
                            Text(text = stringResource(id = R.string.note_name_label))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Next)
                        }),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.note_value_label),
                                fontSize = 20.sp
                            )

                            OutlinedTextField(
                                value = noteText,
                                onValueChange = { newValue ->
                                    noteText = newValue
                                    noteValue = noteText.toDoubleOrNull()
                                    isValidNote = noteValue != null
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.note_percentage_label),
                                fontSize = 20.sp
                            )

                            OutlinedTextField(
                                value = percentageText,
                                onValueChange = { newValue ->
                                    percentageText = newValue
                                    percentageValue = percentageText.toDoubleOrNull()
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                if (changed) {
                    IconButton(
                        onClick = {
                            if (isValidNote) {
                                val updatedNote = note.copy(
                                    noteName = noteNameText,
                                    note = noteValue!!,
                                    percentage = percentageValue
                                )

                                onUpdate(updatedNote)
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Green),
                        enabled = isValidNote
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                }

                IconButton(
                    onClick = {
                        deleted = true
                        visibleState.targetState = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = Color.Red,
                        contentDescription = null
                    )
                }
            }
        }
    }
}