package test.com.h2rd.refactoring.integration;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.web.UserResource;
import java.util.Arrays;
import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

public class UserIntegrationTest {

    private UserResource userResource;

    /* clear added user after each test */
    @After
    public void reset() {
        User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(Arrays.asList("role1", "role2"));

        userResource.deleteUser(integration.getName(), integration.getEmail(), integration.getRoles());

    }

    /**
     * Unit test for getUsers
     */
    @Test
    public void getUsersTest() {

        userResource = new UserResource();

        User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(Arrays.asList("role1", "role2"));

        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        response = userResource.getUsers();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    /**
     * Unit test for addUser. The user should be saved without errors
     */
    @Test
    public void addUserTest() {
        userResource = new UserResource();

        User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(Arrays.asList("role1", "role2"));

        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        response = userResource.findUser("initial@integration.com");
        Assert.assertEquals(integration, response.getEntity());

    }

    /**
     * Unit test for updateUser. The user's name should be changed without error
     *
     */
    @Test
    public void updateUserTest() {
        userResource = new UserResource();

        User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(Arrays.asList("role1", "role2"));

        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        /* update the user */
        integration.setName("New Fake Name");
        response = userResource.updateUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        /* test if the change has been updated */
        response = userResource.findUser("initial@integration.com");
        Assert.assertEquals(integration.getName(), ((User) response.getEntity()).getName());

    }

    /**
     * Unit test for deleteUser. The user should be deleted without errors
     */
    @Test
    public void deleteUserTest() {
        userResource = new UserResource();

        User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(Arrays.asList("role1", "role2"));

        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        /* delete the user */
        response = userResource.deleteUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        /* test if the user is deleted */
        response = userResource.findUser("initial@integration.com");
        Assert.assertNull(response.getEntity());
    }

    /**
     * Unit test for findUser. If the user is in the list, it should be found
     * without errors
     */
    @Test
    public void findUserTest() {
        userResource = new UserResource();

        User integration = new User();
        integration.setName("integration");
        integration.setEmail("initial@integration.com");
        integration.setRoles(Arrays.asList("role1", "role2"));

        Response response = userResource.addUser(integration.getName(), integration.getEmail(), integration.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        response = userResource.findUser("initial@integration.com");
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Assert.assertEquals(integration, (User) response.getEntity());

    }
}
