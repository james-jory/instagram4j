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
