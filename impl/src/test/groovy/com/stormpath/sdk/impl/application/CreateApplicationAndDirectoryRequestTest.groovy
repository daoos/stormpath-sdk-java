/*
 * Copyright 2013 Stormpath, Inc. and contributors.
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
package com.stormpath.sdk.impl.application

import com.stormpath.sdk.application.Application
import org.testng.annotations.Test

import static org.easymock.EasyMock.createStrictMock
import static org.testng.Assert.*

/**
 * @since 0.8
 */
class CreateApplicationAndDirectoryRequestTest {

    @Test
    void testAll() {

        def app = createStrictMock(Application)
        def request = new CreateApplicationAndDirectoryRequest(app, "directoryName")

        assertSame(request.application, app)
        assertEquals(request.getDirectoryName(), "directoryName")

        request.accept(new CreateApplicationRequestVisitor() {
            @Override
            void visit(DefaultCreateApplicationRequest defaultRequest) {
                fail("shouldn't have received a " + defaultRequest.class.name)
            }

            @Override
            void visit(CreateApplicationAndDirectoryRequest createApplicationAndDirectoryRequest) {
                assertSame(createApplicationAndDirectoryRequest, request)
            }
        })

    }
}
