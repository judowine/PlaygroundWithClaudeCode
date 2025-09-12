package com.example.playground.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playground.presentation.state.TodoListUiState
import com.example.playground.presentation.viewmodel.TodoListViewModel
import com.example.playground.shared.domain.model.TodoItem

/**
 * TODO一覧画面
 * Design Docの仕様に基づくMaterial Design 3準拠UI（スケルトン実装）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel,
    onNavigateToEdit: (Long?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // TODO: 実際のSnackbarHost実装
    val snackbarHostState = remember { SnackbarHostState() }

    // エラー表示処理
    state.errorMessage?.let { error ->
        LaunchedEffect(error) {
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEdit(null) }
            ) {
                // TODO: アイコン追加
                Text("+")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("TODOリスト") },
                actions = {
                    // TODO: フィルターボタン実装
                    TextButton(
                        onClick = { viewModel.toggleCompletionFilter() }
                    ) {
                        Text(if (state.showCompletedOnly) "全て" else "完了済み")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                LoadingContent(modifier = modifier.padding(paddingValues))
            }
            state.isEmpty -> {
                EmptyStateContent(modifier = modifier.padding(paddingValues))
            }
            else -> {
                TodoListContent(
                    todos = state.filteredTodos,
                    onTodoClick = { onNavigateToEdit(it.id) },
                    onToggleComplete = viewModel::toggleTodoCompletion,
                    onDeleteTodo = viewModel::deleteTodo,
                    modifier = modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyStateContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "タスクがありません",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "+ボタンから新しいタスクを作成してください",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TodoListContent(
    todos: List<TodoItem>,
    onTodoClick: (TodoItem) -> Unit,
    onToggleComplete: (Long) -> Unit,
    onDeleteTodo: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = todos,
            key = { it.id }
        ) { todo ->
            // TODO: 実際のTodoListItemコンポーネント実装
            Card(
                onClick = { onTodoClick(todo) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (todo.description.isNotEmpty()) {
                        Text(
                            text = todo.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (todo.isCompleted) "完了" else "未完了",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Row {
                            TextButton(
                                onClick = { onToggleComplete(todo.id) }
                            ) {
                                Text(if (todo.isCompleted) "未完了" else "完了")
                            }
                            TextButton(
                                onClick = { onDeleteTodo(todo.id) }
                            ) {
                                Text("削除")
                            }
                        }
                    }
                }
            }
        }
    }
}