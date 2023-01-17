package com.jeongseunggyu.devjeongseungyusns.network

import com.jeongseunggyu.devjeongseungyusns.network.data.AuthRequest
import com.jeongseunggyu.devjeongseungyusns.network.data.AuthResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

object AuthRepository {

    private const val registerUrl = "https://okauhcknaytmqigpoabr.supabase.co/auth/v1/signup"
    private const val loginUrl = "https://okauhcknaytmqigpoabr.supabase.co/auth/v1/token"

    private const val TAG: String = "AuthRepository"

    suspend fun register(email: String, password: String) : HttpResponse{

        return KtorClient.httpClient.post(registerUrl){
            setBody(AuthRequest(email, password))
        }
    }

    suspend fun login(email: String, password: String) : HttpResponse{

        return KtorClient.httpClient.post(loginUrl){
            url{
                parameters.append("grant_type", "password")
            }
            setBody(AuthRequest(email, password))
        }
    }
}