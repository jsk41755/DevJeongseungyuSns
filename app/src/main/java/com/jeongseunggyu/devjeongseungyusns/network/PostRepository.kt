package com.jeongseunggyu.devjeongseungyusns.network

import com.jeongseunggyu.devjeongseungyusns.BuildConfig
import com.jeongseunggyu.devjeongseungyusns.network.data.AuthRequest
import com.jeongseunggyu.devjeongseungyusns.network.data.Post
import com.jeongseunggyu.devjeongseungyusns.network.data.PostEditRequest
import com.jeongseunggyu.devjeongseungyusns.network.data.PostRequest
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.headers

object PostRepository {

    private const val postUrl = "https://okauhcknaytmqigpoabr.supabase.co/rest/v1/posts"

    private const val TAG: String = "PostRepository"

    //모든 포스트 가져오기
    suspend fun fetchAllPosts() : List<Post> {
        return KtorClient.httpClient.get(postUrl){
            url{
                parameters.append("select", "*")
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPABASE_KEY}")
            }
        }.body<List<Post>>()
    }

    //단일 포스트 가져오기
    suspend fun fetchPostItem(postId: String) : Post {
        return KtorClient.httpClient.get(postUrl){
            url{
                parameters.append("select", "*")
                parameters.append("id", "eq.$postId")
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPABASE_KEY}")
            }
        }.body<List<Post>>()[0]
    }

    //단일 포스트 삭제
    suspend fun deletePostItem(postId: String) : HttpResponse {
        return KtorClient.httpClient.delete(postUrl){
            url{
                parameters.append("id", "eq.$postId")
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPABASE_KEY}")
            }
        }
    }

    //단일 포스트 추가하기
    suspend fun addPostItem(
        title: String,
        content: String? = null
    ) : HttpResponse {
        return KtorClient.httpClient.post(postUrl){

            headers{
                //UserInfo.accessToken 다른 사용자 접근을 금지하고 싶을 때
                headers.append("Authorization", "Bearer ${BuildConfig.SUPABASE_KEY}")
            }
            setBody(PostRequest(title, content, UserInfo.userId))
        }
    }

    //단일 포스트 수정하기
    suspend fun editPostItem(
        postId: String,
        title: String,
        content: String? = null
    ) : HttpResponse {
        val response = KtorClient.httpClient.patch(postUrl){
            url {
                parameters.append("id", "eq.$postId")
            }
            headers{
                headers.append("Authorization", "Bearer ${BuildConfig.SUPABASE_KEY}")
                headers.append("Prefer", "return=representation")
            }
            setBody(PostEditRequest(title, content))
        }
        return response
    }

}