/*
 * Copyright 2016 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.sdk.client;

/**
 * @since 1.0.0
 */
public class AuthenticationSchemes {

    public static final AuthenticationScheme BASIC = newBasicAuthenticationScheme();
    public static final AuthenticationScheme SAUTHC1 = newSAuthc1AuthenticationScheme();

    private static AuthenticationScheme newBasicAuthenticationScheme() {
        return new BasicAuthenticationScheme();
    }

    private static AuthenticationScheme newSAuthc1AuthenticationScheme() {
        return new SAuthc1AuthenticationScheme();
    }

}