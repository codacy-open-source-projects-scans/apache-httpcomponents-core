/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.hc.core5.testing.compatibility;

import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.testing.compatibility.classic.HttpBinClassicCompatTest;
import org.apache.hc.core5.testing.compatibility.nio.HttpBinAsyncCompatTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
class HttpBinCompatIT {

    private static Network NETWORK = Network.newNetwork();
    @Container
    static final GenericContainer<?> HTTP_BIN_CONTAINER = ContainerImages.httpBin(NETWORK);

    @AfterAll
    static void cleanup() {
        HTTP_BIN_CONTAINER.close();
    }

    static HttpHost targetContainerHost() {
        return new HttpHost(URIScheme.HTTP.id, HTTP_BIN_CONTAINER.getHost(), HTTP_BIN_CONTAINER.getMappedPort(ContainerImages.HTTP_PORT));
    }

    @Nested
    @DisplayName("Classic")
    class Classic extends HttpBinClassicCompatTest {

        public Classic() throws Exception {
            super(targetContainerHost());
        }

    }

    @Nested
    @DisplayName("Async")
    class Async extends HttpBinAsyncCompatTest {

        public Async() throws Exception {
            super(targetContainerHost());
        }

    }

}
