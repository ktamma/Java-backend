package util

import java.lang.IllegalStateException
import java.io.IOException
import java.io.InputStream
import java.lang.RuntimeException
import java.nio.charset.StandardCharsets
import java.util.*

object FileUtil {
    @JvmStatic
    fun readFileFromClasspath(pathOnClasspath: String): String {
        try {
            Thread.currentThread()
                    .contextClassLoader
                    .getResourceAsStream(pathOnClasspath).use { `is` ->
                        checkNotNull(`is`) { "can't load file: $pathOnClasspath" }
                        return readStream(`is`)
                    }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun readStream(`is`: InputStream?): String {
        Scanner(`is`, StandardCharsets.UTF_8.name()).use { scanner -> return scanner.useDelimiter("\\A").next() }
    }
}