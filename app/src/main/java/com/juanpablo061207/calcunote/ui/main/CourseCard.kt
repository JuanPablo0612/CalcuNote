package com.juanpablo061207.calcunote.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.juanpablo061207.calcunote.R
import com.juanpablo061207.calcunote.ui.common.Note
import com.juanpablo061207.calcunote.ui.common.getNoteColor
import com.juanpablo061207.calcunote.ui.common.getNoteValueAsText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseCard(
    courseUiState: CourseUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val visibleState = remember { MutableTransitionState(false) }
    var deleted by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        visibleState.targetState = true
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
            border = BorderStroke(2.dp, getNoteColor(courseUiState.yearFinalNote)),
            onClick = onClick,
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.weight(4f)
                ) {
                    Text(
                        text = courseUiState.courseWithNotes.course.name,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Text(
                        text = stringResource(
                            id = R.string.course_number_of_notes_label,
                            courseUiState.courseWithNotes.notes.size
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (courseUiState.courseWithNotes.notes.isNotEmpty()) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.course_status_label),
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = stringResource(id = getNoteValueAsText(courseUiState.yearFinalNote)),
                                style = MaterialTheme.typography.titleLarge,
                                color = getNoteColor(courseUiState.yearFinalNote)
                            )
                        }
                    }
                }

                if (courseUiState.courseWithNotes.notes.isNotEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Note(
                            note = courseUiState.yearFinalNote,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                IconButton(
                    onClick = {
                        deleted = true
                        visibleState.targetState = false
                    }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        }
    }
}