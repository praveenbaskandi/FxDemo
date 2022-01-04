package com.praveen.funxdemo.graphUtil

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null

fun apolloClient(): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
        .build()

    instance = ApolloClient.Builder()
        .serverUrl("https://explorer.functionx.io/explorer/graphql")
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}