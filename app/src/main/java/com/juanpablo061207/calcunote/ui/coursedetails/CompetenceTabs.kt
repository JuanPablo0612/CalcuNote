package com.juanpablo061207.calcunote.ui.coursedetails

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.juanpablo061207.calcunote.ui.common.getNoteTypeTextId

@Composable
fun CompetenceTabs(selectedNotesType: Int, onSelectedNotesTypeChanged: (index: Int) -> Unit) {
    TabRow(selectedTabIndex = selectedNotesType) {
        (0..2).forEach { index ->
            Tab(
                text = {
                    Text(text = stringResource(id = getNoteTypeTextId(index)))
                },
                selected = selectedNotesType == index,
                onClick = { onSelectedNotesTypeChanged(index) }
            )
        }
    }
}