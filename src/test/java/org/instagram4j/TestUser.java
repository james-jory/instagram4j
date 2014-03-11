package org.instagram4j;

import org.instagram4j.entity.User;
import org.junit.Assert;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestUser extends TestBase {
    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TestUser.class);
    }

    public void testCurrentUser() throws InstagramException {
    	InstagramClient client = createClient();
    	
    	Result<User> result = client.getCurrentUser();
    	Assert.assertNotNull(result);
    	Assert.assertNotNull(result.getMeta());
    	Assert.assertTrue(result.getMeta().isSuccess());
    	Assert.assertNotNull(result.getData());
    	
    	Assert.assertEquals("James Jory", result.getData().getFullName());
    }
}
