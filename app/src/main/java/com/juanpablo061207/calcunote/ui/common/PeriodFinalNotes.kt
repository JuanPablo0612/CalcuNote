package com.juanpablo061207.calcunote.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.juanpablo061207.calcunote.R
import com.juanpablo061207.calcunote.domain.model.Period

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PeriodFinalNotes(period: Period, modifier: Modifier = Modifier) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Note(note = period.toBeFinalNote, modifier = Modifier.size(50.dp))
            Text(
                text = stringResource(id = R.string.competence_to_be),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Note(note = period.toKnowFinalNote, modifier = Modifier.size(50.dp))
            Text(
                text = stringResource(id = R.string.competence_to_know),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Note(note = period.toDoFinalNote, modifier = Modifier.size(50.dp))
            Text(
                text = stringResource(id = R.string.competence_to_do),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}