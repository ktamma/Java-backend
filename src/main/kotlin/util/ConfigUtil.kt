package util

import util.ConnectionInfo
import java.util.Properties
import util.PropertyLoader

object ConfigUtil {
    fun readConnectionInfo(): ConnectionInfo {
        val properties = PropertyLoader.loadApplicationProperties()
        return ConnectionInfo(
                properties.getProperty("dbUrl"),
                properties.getProperty("dbUser"),
                properties.getProperty("dbPassword"))
    }
}