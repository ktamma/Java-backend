package util

import lombok.AllArgsConstructor
import lombok.Value

@Value
@AllArgsConstructor
class ConnectionInfo (
    private val url: String? = null,
    private val user: String? = null,
    private val pass: String? = null,
)