package controller

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import exceptions.ValidationErrors
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@RestControllerAdvice
class ControllerErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(
            exception: MethodArgumentNotValidException): ValidationErrors {
        val errors: MutableList<FieldError> = exception.bindingResult.fieldErrors
        val result = ValidationErrors()
        for (error in errors) {
            result.addFieldError(error!!)
        }
        return result
    }
}