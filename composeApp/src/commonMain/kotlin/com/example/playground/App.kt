package com.example.playground

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.playground.feature.todo.presentation.navigation.SimpleTodoNavigation

/**
 * Main application composable
 * Integrates the Todo feature as the primary application interface
 */
@Composable
@Preview
fun App() {
    MaterialTheme {
        // For PBI-001, we'll directly show the Todo feature as the main app
        // In a larger app, this would be part of a larger navigation structure
        
        var showTodoApp by remember { mutableStateOf(false) }
        
        if (showTodoApp) {
            // Show the Todo application
            TodoApp()
        } else {
            // Show the app launcher/demo screen
            AppLauncher(
                onLaunchTodoApp = { showTodoApp = true }
            )
        }
    }
}

/**
 * Main Todo application interface
 * This will be the primary interface for PBI-001
 */
@Composable
private fun TodoApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        SimpleTodoNavigation(
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * App launcher screen for demonstrating the todo feature
 * This provides a clean entry point for the PBI-001 demo
 */
@Composable
private fun AppLauncher(
    onLaunchTodoApp: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Title
            Text(
                text = "Playground",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Kotlin Multiplatform Demo",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Feature Cards
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // TODO: Replace with actual icon when icons dependency is added
                    Text(
                        text = "📝",
                        style = MaterialTheme.typography.displayMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "PBI-001: Local TODO App",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "A complete local todo application built with Kotlin Multiplatform and Compose Multiplatform. Features include creating, editing, deleting, and managing todo items with local SQLite persistence.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = onLaunchTodoApp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("📝")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Launch Todo App")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Additional Info
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Architecture Highlights",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "• Layered Architecture (Presentation, Domain, Data)\n" +
                               "• MVVM with Compose State Management\n" +
                               "• Material Design 3 Components\n" +
                               "• Local SQLite Database (Room)\n" +
                               "• Comprehensive Error Handling\n" +
                               "• Accessibility Support",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Preview for the app launcher
 */
@Composable
@Preview
private fun AppLauncherPreview() {
    MaterialTheme {
        AppLauncher(onLaunchTodoApp = {})
    }
}