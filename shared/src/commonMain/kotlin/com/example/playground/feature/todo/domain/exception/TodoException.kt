package com.example.playground.feature.todo.domain.exception

/**
 * Base sealed class for all TODO-related exceptions.
 * Provides structured error handling throughout the TODO feature.
 */
sealed class TodoException : Exception() {
    
    /**
     * Validation error for invalid input data
     * @param field The field that failed validation
     * @param reason The specific reason for validation failure
     */
    data class ValidationException(
        val field: String,
        val reason: String
    ) : TodoException() {
        override val message: String = "Validation failed for field '$field': $reason"
    }
    
    /**
     * Exception when a requested TODO item is not found
     * @param todoId The ID of the TODO item that was not found
     */
    data class NotFoundException(
        val todoId: Long
    ) : TodoException() {
        override val message: String = "Todo item with ID $todoId not found"
    }
    
    /**
     * Database operation error
     * @param operation The database operation that failed
     * @param cause The underlying cause of the error
     */
    data class DatabaseException(
        val operation: String,
        override val cause: Throwable? = null
    ) : TodoException() {
        override val message: String = "Database operation '$operation' failed"
    }
    
    /**
     * Network-related error (future use for cloud sync)
     * @param operation The network operation that failed
     * @param cause The underlying cause of the error
     */
    data class NetworkException(
        val operation: String,
        override val cause: Throwable? = null
    ) : TodoException() {
        override val message: String = "Network operation '$operation' failed"
    }
}

/**
 * Legacy exception for backward compatibility
 * @deprecated Use TodoException.ValidationException instead
 */
@Deprecated("Use TodoException.ValidationException instead")
class ValidationException(message: String) : Exception(message)

/**
 * Legacy exception for backward compatibility  
 * @deprecated Use TodoException.NotFoundException instead
 */
@Deprecated("Use TodoException.NotFoundException instead")
class NotFoundException(message: String) : Exception(message)