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

package org.apache.hc.core5.http.io.ssl;

import javax.net.ssl.SSLParameters;

import org.apache.hc.core5.function.Callback;
import org.apache.hc.core5.http.URIScheme;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.http.ssl.TlsCiphers;

/**
 * Default TLS session setup handler.
 *
 * @since 5.0
 */
public final class DefaultTlsSetupHandler implements Callback<SSLParameters> {

    public final static DefaultTlsSetupHandler SERVER = new DefaultTlsSetupHandler(false);
    public final static DefaultTlsSetupHandler CLIENT = new DefaultTlsSetupHandler(true);

    private final boolean client;

    public DefaultTlsSetupHandler() {
        this.client = false;
    }

    /**
     * @since 5.3
     */
    public DefaultTlsSetupHandler(final boolean client) {
        this.client = client;
    }

    @Override
    public void execute(final SSLParameters sslParameters) {
        sslParameters.setProtocols(TLS.excludeWeak(sslParameters.getProtocols()));
        sslParameters.setCipherSuites(TlsCiphers.excludeWeak(sslParameters.getCipherSuites()));
        if (client) {
            sslParameters.setEndpointIdentificationAlgorithm(URIScheme.HTTPS.id);
        }
    }

}
