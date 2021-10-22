package util

import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*

object Util {
    fun readStream(`is`: InputStream?): String {
        Scanner(`is`,
                StandardCharsets.UTF_8.name()).useDelimiter("\\A").use { scanner -> return if (scanner.hasNext()) scanner.next() else "" }
    }
}