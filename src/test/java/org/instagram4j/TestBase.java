/**
 * Copyright (C) 2014 James Jory (james.b.jory@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.instagram4j;

import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

public abstract class TestBase extends TestCase {
	protected final Properties props = new Properties();

	public TestBase() {
		super();
	}

	public TestBase(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		InputStream is = TestBase.class.getResourceAsStream("/test.properties");
        props.load(is);
        is.close();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected InstagramClient createClient() {
		return new DefaultInstagramClient(props.getProperty("client.id"), props.getProperty("client.secret"), props.getProperty("access.token"));
	}
	
	protected InstagramClient createTokenlessClient() {
		return new DefaultInstagramClient(props.getProperty("client.id"), props.getProperty("client.secret"));
	}
}
