package com.bigcake.githubusers.data.util

import okhttp3.HttpUrl.Companion.toHttpUrl

object LinkHeaderHelper {
    fun parse(linkHeader: String?): Map<String, Link> {
        if (linkHeader.isNullOrEmpty()) {
            return mapOf()
        }

        val result = mutableMapOf<String, Link>()
        // Link header format: <https://api.github.com/users?since=30&per_page=20>; rel="next", <https://api.github.com/users{?since}>; rel="first"
        val parts = linkHeader.split(",").map { it.trim() }
        parts.forEach { part ->
            val match = Regex("<(.+)>; rel=\"(\\w+)\"").find(part)
            match?.let {
                val (url, relation) = match.destructured
                val httpUrl = url.toHttpUrl()
                val link = Link(
                    url = url,
                    since = httpUrl.queryParameter("since")?.toInt() ?: -1,
                    perPage = httpUrl.queryParameter("per_page")?.toInt() ?: -1
                )
                result[relation] = link
            }
        }
        return result
    }
}

data class Link(
    val url: String? = null,
    val since: Int = -1,
    val perPage: Int = -1
)