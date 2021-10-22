package exceptions


import com.fasterxml.jackson.annotation.JsonInclude


@JsonInclude(JsonInclude.Include.NON_NULL)
data class ValidationError (
    var code: String? = null,
    var arguments: List<String>? = null
)