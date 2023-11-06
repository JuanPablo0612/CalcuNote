package com.juanpablo061207.calcunote.ui.coursedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juanpablo061207.calcunote.R
import com.juanpablo061207.calcunote.ui.animation.ShowAndHideContentAnimation
import com.juanpablo061207.calcunote.ui.common.Note
import com.juanpablo061207.calcunote.ui.common.PeriodFinalNotes
import com.juanpablo061207.calcunote.ui.common.YearFinalNotes
import com.juanpablo061207.calcunote.ui.common.getPeriodTextId
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailsScreen(viewModel: CourseDetailsViewModel = hiltViewModel(), courseId: Long) {
    val uiState = viewModel.uiState
    val selectedNotes =
        uiState.notes.filter { it.competenceId == uiState.selectedCompetenceId && it.periodId == uiState.selectedPeriodId }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    LaunchedEffect(key1 = true) {
        if (courseId != 0L) {
            viewModel.start(courseId)
            viewModel.onEditCourseNameChanged()
        }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.course_details_title))
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                if (uiState.editCourseName) {
                    EditCourseName(
                        courseName = uiState.courseName,
                        isValidCourseName = uiState.isValidCourseName,
                        onUpdate = { viewModel.onCourseNameTextChanged(it) },
                        onSave = { viewModel.onSaveCourse() }
                    )
                } else {
                    CourseName(
                        courseName = uiState.courseName,
                        onEditClicked = { viewModel.onEditCourseNameChanged() }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column {
                    TextButton(
                        onClick = { viewModel.onTogglePeriodSelector() },
                        modifier = Modifier.fillParentMaxWidth()
                    ) {
                        Text(text = stringResource(id = getPeriodTextId(uiState.selectedPeriodId)))

                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = uiState.isPeriodSelectorExpanded,
                        onDismissRequest = { viewModel.onTogglePeriodSelector() },
                        modifier = Modifier.fillParentMaxWidth()
                    ) {
                        (0..3).forEach { index ->
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = getPeriodTextId(index))) },
                                onClick = {
                                    viewModel.onSelectedPeriodIdChanged(index)
                                    viewModel.onTogglePeriodSelector()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            if (uiState.selectedPeriodId == 3) {
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        YearFinalNotes(
                            yearFinalNotes = uiState.yearFinalNotes,
                            modifier = Modifier.weight(3f)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Note(
                                note = uiState.yearFinalNotes.finalNote,
                                modifier = Modifier.size(80.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.course_final_note_label),
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            item {
                ShowAndHideContentAnimation(visible = uiState.notes.any { it.periodId == uiState.selectedPeriodId }) {
                    val periodNotes = when (uiState.selectedPeriodId) {
                        0 -> uiState.yearFinalNotes.firstPeriod
                        1 -> uiState.yearFinalNotes.secondPeriod
                        else -> uiState.yearFinalNotes.thirdPeriod
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        PeriodFinalNotes(
                            period = periodNotes,
                            modifier = Modifier.weight(3f)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Note(note = periodNotes.finalNote, modifier = Modifier.size(80.dp))
                            Text(
                                text = stringResource(id = R.string.course_final_period_note_label),
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            if (uiState.selectedPeriodId != 3) {
                item {
                    Text(
                        text = stringResource(id = R.string.course_notes_label),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillParentMaxWidth()
                    )

                    CompetenceTabs(
                        selectedNotesType = uiState.selectedCompetenceId,
                        onSelectedNotesTypeChanged = { viewModel.onSelectedNotesTypeChanged(it) }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(
                    items = selectedNotes,
                    contentType = { "note" },
                    key = { note -> note.id }
                ) { note ->
                    NoteItem(
                        note = note,
                        onUpdate = { updatedNote ->
                            viewModel.onNoteUpdated(updatedNote)
                        },
                        onDelete = {
                            viewModel.onNoteDeleted(note)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    Button(
                        enabled = uiState.courseId != 0L,
                        onClick = {
                            viewModel.onNoteAdded()
                            coroutineScope.launch {
                                lazyListState.scrollToItem(selectedNotes.size)
                            }
                        },
                        modifier = Modifier.fillParentMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.course_add_note_button))
                    }
                }
            }
        }
    }
}