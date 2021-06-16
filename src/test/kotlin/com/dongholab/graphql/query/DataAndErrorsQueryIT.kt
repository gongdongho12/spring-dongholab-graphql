/*
 * Copyright 2021 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dongholab.graphql.query

import com.dongholab.graphql.DATA_JSON_PATH
import com.dongholab.graphql.ERRORS_JSON_PATH
import com.dongholab.graphql.EXTENSIONS_JSON_PATH
import com.dongholab.graphql.GRAPHQL_ENDPOINT
import com.dongholab.graphql.GRAPHQL_MEDIA_TYPE
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(PER_CLASS)
class DataAndErrorsQueryIT(@Autowired private val testClient: WebTestClient) {

    @ParameterizedTest
    @ValueSource(strings = ["returnDataAndErrors", "completableFutureDataAndErrors"])
    fun `verify data and errors queries`(query: String) {
        val expectedData = "Hello from data fetcher"
        val expectedError = "data and errors"

        testClient.post()
            .uri(GRAPHQL_ENDPOINT)
            .accept(APPLICATION_JSON)
            .contentType(GRAPHQL_MEDIA_TYPE)
            .bodyValue("query { $query }")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$DATA_JSON_PATH.$query").isEqualTo(expectedData)
            .jsonPath("$ERRORS_JSON_PATH.[0].message").isEqualTo(expectedError)
            .jsonPath(EXTENSIONS_JSON_PATH).doesNotExist()
    }
}