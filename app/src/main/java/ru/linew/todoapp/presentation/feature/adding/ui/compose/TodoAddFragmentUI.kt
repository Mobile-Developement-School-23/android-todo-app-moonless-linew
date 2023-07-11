package ru.linew.todoapp.presentation.feature.adding.ui.compose

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.YandexTodoTheme
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.body
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.buttonText
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.subhead

@Preview
@Composable
fun AddTodoUiWithTheme() {
    YandexTodoTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(), color = YandexTodoTheme.colors.backPrimary
        ) {
            AddTodoUi()
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTodoUi() {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val dialogState = rememberMaterialDialogState()
    val checkState = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    if (checkState.value){
        dialogState.show()
    }

        TodoPriorityChooserBottomSheet(sheetState = sheetState, coroutineScope = coroutineScope) {
            Column {
                TodoToolBar()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(top = 8.dp)
                ) {
                    TodoCardTextEdit()
                    TodoChoosePriorityButton(sheetState, coroutineScope)
                    TodoMakeUntilDivider()
                    TodoMakeUntilSwitch(checkState = checkState)
                    TodoDeleteDivider()
                    TodoDeleteButton()
                    TodoDatePicker(dialogState = dialogState) {
                        
                    }
                }
            }
        }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoChoosePriorityButton(sheetState: ModalBottomSheetState, coroutineScope: CoroutineScope) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(color = YandexTodoTheme.colors.labelPrimary)
            ) { coroutineScope.launch { sheetState.show() } }
            .padding(12.dp),
    ) {
        Text(text = stringResource(id = R.string.priority), style = MaterialTheme.typography.body)
        Text(text = stringResource(id = R.string.no), style = MaterialTheme.typography.subhead)
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
fun TodoMakeUntilSwitch(checkState: MutableState<Boolean>) {

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
            Text(
                text = stringResource(id = R.string.test_date),
                style = MaterialTheme.typography.buttonText
            )
        }
        Switch(
            checked = checkState.value,
            onCheckedChange = { checkState.value = !checkState.value },
            colors = SwitchDefaults.colors(checkedThumbColor = YandexTodoTheme.colors.colorBlue)
        )
    }
}


@Composable
fun TodoDeleteButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple(color = YandexTodoTheme.colors.labelPrimary)
            ) { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
fun TodoCardTextEdit() {
    val input = remember { mutableStateOf("") }
    Card(
        backgroundColor = YandexTodoTheme.colors.backSecondary,
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        BasicTextField(
            value = input.value,
            onValueChange = { input.value = it },
            modifier = Modifier
                .defaultMinSize(minHeight = 104.dp)
                .padding(8.dp)
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.body,
            cursorBrush = SolidColor(YandexTodoTheme.colors.labelPrimary),
            decorationBox = {
                if (input.value.isEmpty()) Text(
                    text = stringResource(id = R.string.what_to_do),
                    color = YandexTodoTheme.colors.labelTertiary
                )
                it.invoke()
            }
        )
    }
}

@Composable
fun TodoToolBar() {
    TopAppBar(
        title = {},
        elevation = 0.dp,
        backgroundColor = YandexTodoTheme.colors.backPrimary,
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    tint = YandexTodoTheme.colors.labelPrimary,
                )
            }

        },
        actions = {
            TextButton(onClick = { }) {
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
    content: @Composable () -> Unit

) {

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = YandexTodoTheme.colors.backPrimary,
        sheetContent = {
            TodoPriorityItem(
                text = stringResource(id = R.string.low),
                painter = painterResource(id = R.drawable.low_priority)
            ) { coroutineScope.launch { sheetState.hide() } }
            TodoPriorityItem(
                text = stringResource(id = R.string.no),
                painter = null
            ) { coroutineScope.launch { sheetState.hide() } }

            TodoPriorityItem(
                text = stringResource(id = R.string.high),
                painter = painterResource(id = R.drawable.high_priority)
            ) { coroutineScope.launch { sheetState.hide() } }

        }, content = content
    )
}

@Composable
fun TodoPriorityItem(text: String, painter: Painter?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        if (painter != null) Image(
            painter = painter,
            contentDescription = stringResource(id = R.string.priority_icon_description)
        )
        Text(text = text, color = YandexTodoTheme.colors.labelPrimary)
    }
}

@Composable
fun TodoDatePicker(dialogState: MaterialDialogState, content: @Composable () -> Unit) {
    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton(text = stringResource(id = R.string.ok))
        negativeButton(text = stringResource(id = R.string.cancel))
    }) {
        content()
        datepicker()
    }
}