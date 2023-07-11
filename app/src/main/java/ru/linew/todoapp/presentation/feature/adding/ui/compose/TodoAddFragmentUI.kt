package ru.linew.todoapp.presentation.feature.adding.ui.compose

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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.YandexTodoTheme
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.body
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.button
import ru.linew.todoapp.presentation.feature.adding.ui.compose.theme.subhead

@Preview
@Composable
fun AddTodoUiTheme() {
    YandexTodoTheme {
        Surface(
            modifier = Modifier.fillMaxSize().systemBarsPadding(), color = YandexTodoTheme.colors.backPrimary
        ) {
            AddTodoUi()
        }
    }
}

@Composable
fun AddTodoUi() {
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
            TodoChoosePriorityButton()
            TodoMakeUntilDivider()
            TodoMakeUntilSwitch()
            TodoDeleteDivider()
            TodoDeleteButton()
        }
    }

}


@Composable
fun TodoChoosePriorityButton() {
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
            ) { }
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
fun TodoMakeUntilSwitch() {
    val isChecked = remember { mutableStateOf(true) }
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
                style = MaterialTheme.typography.button
            )
        }
        Switch(checked = isChecked.value, onCheckedChange = {isChecked.value = !isChecked.value})
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
            style = MaterialTheme.typography.button,
            color = YandexTodoTheme.colors.colorRed,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun TodoCardTextEdit() {
    val input = remember { mutableStateOf("") }
    Card(
        colors = CardDefaults.cardColors(containerColor = YandexTodoTheme.colors.backSecondary),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoToolBar() {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = YandexTodoTheme.colors.backPrimary),
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
                    style = MaterialTheme.typography.button
                )
            }
        }

    )
}