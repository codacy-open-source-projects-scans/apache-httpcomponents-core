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
package org.apache.hc.core5.http2.config;

/**
 * HTTP/2 protocol parameters.
 *
 * @since 5.0
 */
public enum H2Param {

    HEADER_TABLE_SIZE(0x1),
    ENABLE_PUSH(0x2),
    MAX_CONCURRENT_STREAMS(0x3),
    INITIAL_WINDOW_SIZE(0x4),
    MAX_FRAME_SIZE(0x5),
    MAX_HEADER_LIST_SIZE(0x6),
    SETTINGS_NO_RFC7540_PRIORITIES (0x9);

    int code;

    H2Param(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private static final H2Param[] LOOKUP_TABLE;
    static {
        int max = 0;
        for (final H2Param p : H2Param.values()) {
            if (p.code > max) {
                max = p.code;
            }
        }
        LOOKUP_TABLE = new H2Param[max + 1];
        for (final H2Param p : H2Param.values()) {
            LOOKUP_TABLE[p.code] = p;
        }
    }

    public static H2Param valueOf(final int code) {
        if (code < 0 || code >= LOOKUP_TABLE.length) {
            return null;
        }
        return LOOKUP_TABLE[code];
    }

    public static String toString(final int code) {
        if (code < 0 || code >= LOOKUP_TABLE.length || LOOKUP_TABLE[code] == null) {
            return Integer.toString(code);
        }
        return LOOKUP_TABLE[code].name();
    }

}