package com.bigcake.githubusers.data.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LinkHeaderHelperTest {
    @DisplayName("Should parse link header and return correct results")
    @ParameterizedTest
    @MethodSource("linkHeaderProvider")
    fun parse(linkHeader: String, expected: Map<String, Link>) {
        Assertions.assertEquals(expected, LinkHeaderHelper.parse(linkHeader))
    }

    companion object {
        @JvmStatic
        private fun linkHeaderProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(
                    "link=<https://api.github.com/users?since=30&per_page=20>; rel=\"next\"",
                    mapOf(
                        "next" to Link(
                            "https://api.github.com/users?since=30&per_page=20",
                            30,
                            20
                        )
                    )
                )
            )
        }
    }
}