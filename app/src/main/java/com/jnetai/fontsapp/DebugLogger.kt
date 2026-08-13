package com.jnetai.fontsapp

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

object DebugLogger {
    private const val TAG = "FontsApp"
    private val logHistory = mutableListOf<String>()
    private const val MAX_HISTORY = 500

    fun log(level: String, message: String, throwable: Throwable? = null) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(Date())
        val sb = StringBuilder()
        sb.append("[$timestamp] [$level] $message")
        if (throwable != null) {
            sb.append("\n${getStackTraceString(throwable)}")
        }
        val logEntry = sb.toString()
        logHistory.add(logEntry)
        if (logHistory.size > MAX_HISTORY) {
            logHistory.removeAt(0)
        }
        when (level) {
            "ERROR" -> Log.e(TAG, logEntry)
            "WARN" -> Log.w(TAG, logEntry)
            "DEBUG" -> Log.d(TAG, logEntry)
            else -> Log.i(TAG, logEntry)
        }
    }

    fun d(message: String) = log("DEBUG", message)
    fun i(message: String) = log("INFO", message)
    fun w(message: String) = log("WARN", message)
    fun e(message: String, throwable: Throwable? = null) = log("ERROR", message, throwable)

    fun getStackTraceString(throwable: Throwable): String {
        val sw = StringWriter()
        throwable.printStackTrace(PrintWriter(sw))
        return sw.toString()
    }

    fun getErrorCode(context: String, type: String): String {
        return "ERR_${context.uppercase()}_${type.uppercase()}_${System.currentTimeMillis() % 100000}"
    }

    fun getLogHistory(): List<String> = logHistory.toList()

    fun clearHistory() = logHistory.clear()
}
