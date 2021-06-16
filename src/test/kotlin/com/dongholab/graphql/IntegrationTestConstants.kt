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

package com.dongholab.graphql

import org.springframework.http.MediaType

const val DATA_JSON_PATH = "$.data"
const val ERRORS_JSON_PATH = "$.errors"
const val EXTENSIONS_JSON_PATH = "$.extensions"
const val GRAPHQL_ENDPOINT = "/graphql"
val GRAPHQL_MEDIA_TYPE = MediaType("application", "graphql")
const val SUBSCRIPTION_ENDPOINT = "/subscriptions"
