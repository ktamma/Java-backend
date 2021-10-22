package exceptions

import lombok.AllArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.NoArgsConstructor
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Getter


@JsonInclude(JsonInclude.Include.NON_NULL)
data class ValidationError (
    var code: String? = null,
    var arguments: List<String>? = null
)