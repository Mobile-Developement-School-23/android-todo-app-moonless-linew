package ru.linew.todoapp.presentation.feature.adding.compose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.linew.todoapp.presentation.feature.adding.compose.theme.YandexTodoTheme
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.feature.adding.compose.theme.body
import ru.linew.todoapp.presentation.feature.adding.compose.theme.buttonText
import ru.linew.todoapp.presentation.feature.adding.compose.theme.subhead
import ru.linew.todoapp.presentation.feature.adding.view.viewmodel.state.Result
import ru.linew.todoapp.presentation.model.EditFragmentMode
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.utils.toDateFormat
import java.time.LocalDate
import java.time.ZoneOffset

typealias UiState = State<Result<TodoItem>>

@Composable
fun TodoEditorUiWithTheme(
    uiState: UiState,
    mode: EditFragmentMode,
    onBodyTextChanged: (String) -> Unit,
    onCloseButtonClick: () -> Unit,
    onDateChange: (Long?) -> Unit,
    onSaveButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onPriorityItemClick: (Priority) -> Unit
) {
    YandexTodoTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            color = YandexTodoTheme.colors.backPrimary
        ) {
            if (uiState.value is Result.Complete<TodoItem>) {
                TodoEditorUi(
                    (uiState.value as Result.Complete).result,
                    mode,
                    onBodyTextChanged,
                    onCloseButtonClick,
                    onDateChange,
                    onSaveButtonClick,
                    onDeleteButtonClick,
                    onPriorityItemClick
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoEditorUi(
    uiState: TodoItem,
    editMode: EditFragmentMode,
    onBodyTextChanged: (String) -> Unit,
    onCloseButtonClick: () -> Unit,
    onDateChange: (Long?) -> Unit,
    onSaveButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onPriorityItemClick: (Priority) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val datePickerDialogState = rememberMaterialDialogState()
    val deadlineState = remember { mutableStateOf(uiState.deadlineTime) }
    val bodyText = remember { mutableStateOf(uiState.body) }
    val checkState = remember { mutableStateOf(deadlineState.value != null) }
    val priority = remember { mutableStateOf(uiState.priority) }



    TodoPriorityChooserBottomSheet(
        sheetState = sheetState,
        coroutineScope = coroutineScope,
        onPriorityItemClick = { onPriorityItemClick(it); priority.value = it }) {
        Column {
            TodoToolBar(onCloseButtonClick, onSaveButtonClick)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 8.dp)
            ) {
                TodoCardTextEdit(bodyText.value) { onBodyTextChanged(it); bodyText.value = it }
                TodoChoosePriorityButton(sheetState, coroutineScope, priority.value)
                TodoMakeUntilDivider()
                TodoMakeUntilSwitch(checkState = checkState, deadlineState = deadlineState, onCheckChange = {
                    checkState.value = it
                    if(!it) onDateChange(null) else datePickerDialogState.show()
                })
                TodoDeleteDivider()
                if (editMode == EditFragmentMode.UPDATING) {
                    TodoDeleteButton(onDeleteButtonClick)
                }

                TodoDatePicker(dialogState = datePickerDialogState,
                    onDateChange = { onDateChange(it); deadlineState.value = it },
                    onNegativeButtonClick = { checkState.value = false })
            }
        }
    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoChoosePriorityButton(
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    priority: Priority
) {
    val id = when (priority) {
        Priority.LOW -> R.string.low
        Priority.NO -> R.string.no
        Priority.HIGH -> R.string.high
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                }, indication = rememberRipple(color = YandexTodoTheme.colors.labelPrimary)
            ) { coroutineScope.launch { sheetState.show() } }
            .padding(12.dp),
    ) {
        Text(text = stringResource(id = R.string.priority), style = MaterialTheme.typography.body)
        Text(text = stringResource(id = id), style = MaterialTheme.typography.subhead)
    }
}

@Composable
fun TodoDeleteDivider() {
    Divider(
        thickness = 0.5.dp,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
        color = YandexTodoTheme.colors.supportSeparator
    )
}

@Composable
fun TodoMakeUntilDivider() {
    Divider(
        thickness = 0.5.dp,
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp),
        color = YandexTodoTheme.colors.supportSeparator
    )
}

@Composable
fun TodoMakeUntilSwitch(
    checkState: MutableState<Boolean>,
    deadlineState: MutableState<Long?>,
    onCheckChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.make_until),
                style = MaterialTheme.typography.body
            )
            if (deadlineState.value != null) {
                Text(
                    text = deadlineState.value!!.toDateFormat(),
                    style = MaterialTheme.typography.buttonText
                )
            }
        }
        Switch(
            checked = checkState.value,
            onCheckedChange = { onCheckChange(it); deadlineState.value = null },
            colors = SwitchDefaults.colors(checkedThumbColor = YandexTodoTheme.colors.colorBlue)
        )
    }
}


@Composable
fun TodoDeleteButton(onDeleteButtonClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(
            interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple(color = YandexTodoTheme.colors.labelPrimary)
        ) { onDeleteButtonClick() }
        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = null,
            tint = YandexTodoTheme.colors.colorRed
        )
        Text(
            text = stringResource(id = R.string.delete),
            style = MaterialTheme.typography.buttonText,
            color = YandexTodoTheme.colors.colorRed,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun TodoCardTextEdit(bodyText: String, onBodyTextChanged: (String) -> Unit) {
    Card(
        backgroundColor = YandexTodoTheme.colors.backSecondary,
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        BasicTextField(value = bodyText,
            onValueChange = { onBodyTextChanged(it) },
            modifier = Modifier
                .defaultMinSize(minHeight = 104.dp)
                .padding(8.dp)
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.body,
            cursorBrush = SolidColor(YandexTodoTheme.colors.labelPrimary),
            decorationBox = {
                if (bodyText.isEmpty()) Text(
                    text = stringResource(id = R.string.what_to_do),
                    color = YandexTodoTheme.colors.labelTertiary
                )
                it.invoke()
            })
    }
}

@Composable
fun TodoToolBar(onCloseButtonClick: () -> Unit, onSaveButtonClick: () -> Unit) {
    TopAppBar(title = {},
        elevation = 0.dp,
        backgroundColor = YandexTodoTheme.colors.backPrimary,
        navigationIcon = {
            IconButton(onClick = { onCloseButtonClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    tint = YandexTodoTheme.colors.labelPrimary,
                )
            }

        },
        actions = {
            TextButton(onClick = { onSaveButtonClick() }) {
                Text(
                    text = stringResource(id = R.string.save),
                    color = YandexTodoTheme.colors.colorBlue,
                    style = MaterialTheme.typography.buttonText
                )
            }
        }

    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoPriorityChooserBottomSheet(
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    onPriorityItemClick: (Priority) -> Unit,
    content: @Composable () -> Unit
) {

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = YandexTodoTheme.colors.backPrimary,
        sheetContent = {
            TodoPriorityItem(
                text = stringResource(id = R.string.low),
                priority = Priority.LOW,
                painter = painterResource(id = R.drawable.low_priority)
            ) {
                coroutineScope.launch { sheetState.hide() }
                onPriorityItemClick(it)
            }
            TodoPriorityItem(
                text = stringResource(id = R.string.no), priority = Priority.NO, painter = null
            ) {
                coroutineScope.launch { sheetState.hide() }
                onPriorityItemClick(it)
            }

            TodoPriorityItem(
                text = stringResource(id = R.string.high),
                priority = Priority.HIGH,
                painter = painterResource(id = R.drawable.high_priority)
            ) {
                coroutineScope.launch { sheetState.hide() }
                onPriorityItemClick(it)
            }


        },
        content = content
    )
}

@Composable
fun TodoPriorityItem(
    text: String, priority: Priority, painter: Painter?, onPriorityItemClick: (Priority) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onPriorityItemClick(priority) }
        .padding(16.dp)) {
        if (painter != null) Image(
            painter = painter,
            contentDescription = stringResource(id = R.string.priority_icon_description)
        )
        Text(text = text, color = YandexTodoTheme.colors.labelPrimary)
    }
}

@Composable
fun TodoDatePicker(
    dialogState: MaterialDialogState,
    onNegativeButtonClick: () -> Unit,
    onDateChange: (Long) -> Unit
) {
    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton(text = stringResource(id = R.string.ok))
        negativeButton(text = stringResource(id = R.string.cancel),
            onClick = { onNegativeButtonClick() })
    }) {
        datepicker {
            onDateChange(it.toUnixTime())
        }
    }
}

fun LocalDate.toUnixTime(): Long = this.atStartOfDay().toInstant(ZoneOffset.UTC).epochSecond * 1000