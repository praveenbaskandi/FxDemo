package com.praveen.funxdemo.graphUtil

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null

fun apolloClientUniswap(): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    val okHttpClient = OkHttpClient.Builder()
        .build()

    instance = ApolloClient.Builder()
        .serverUrl("https://api.thegraph.com/subgraphs/name/uniswap/uniswap-v3")
        .okHttpClient(okHttpClient)
        .build()

    return instance!!
}