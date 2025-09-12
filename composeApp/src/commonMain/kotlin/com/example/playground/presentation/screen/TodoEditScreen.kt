package com.example.playground.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playground.presentation.viewmodel.TodoEditViewModel

/**
 * TODO編集/作成画面
 * Design Docの仕様に基づくMaterial Design 3準拠UI（スケルトン実装）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEditScreen(
    viewModel: TodoEditViewModel,
    onNavigateBack: () -> Unit = {},
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
        topBar = {
            TopAppBar(
                title = { 
                    Text(if (state.isNewTodo) "新規タスク" else "タスク編集") 
                },
                navigationIcon = {
                    TextButton(onClick = onNavigateBack) {
                        Text("戻る")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.saveTodo {
                                onNavigateBack()
                            }
                        },
                        enabled = state.canSave
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp)
                            )
                        } else {
                            Text("保存")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        TodoEditContent(
            title = state.title,
            description = state.description,
            onTitleChange = viewModel::updateTitle,
            onDescriptionChange = viewModel::updateDescription,
            isTitleValid = state.isTitleValid,
            modifier = modifier.padding(paddingValues)
        )
    }

    // 未保存の変更がある場合の確認ダイアログ
    // TODO: BackHandlerで戻るボタン処理実装
}

@Composable
private fun TodoEditContent(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    isTitleValid: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("タイトル") },
            isError = !isTitleValid && title.isNotEmpty(),
            supportingText = {
                if (!isTitleValid && title.isNotEmpty()) {
                    Text("タイトルは必須です")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("説明（オプション）") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )
    }
}