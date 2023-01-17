package com.jeongseunggyu.devjeongseungyusns.network

import android.util.Log
import com.jeongseunggyu.devjeongseungyusns.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {

    const val TAG = "API 체크"

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    val httpClient = HttpClient(CIO){
        install(ContentNegotiation){
            json(json)
        }

        install(Logging){
            logger = object : Logger{
                override fun log(message: String) {
                    Log.d(TAG, "api log: $message")
                }
            }
            level = LogLevel.ALL
        }

        install(HttpTimeout){
            requestTimeoutMillis = 15_000
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
        }

        defaultRequest {
            headers.append("Accept", "application/json")
            headers.append("Content-Type", "application/json")
            headers.append("apikey", BuildConfig.SUPABASE_KEY)
        }

    }
}