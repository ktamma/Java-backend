package util

import util.FileUtil.readFileFromClasspath
import java.util.Properties
import java.io.IOException
import java.io.StringReader
import java.lang.RuntimeException

object PropertyLoader {
    fun loadApplicationProperties(): Properties {
        val contents = readFileFromClasspath("application.properties")
        val properties = Properties()
        return try {
            properties.load(StringReader(contents))
            properties
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}