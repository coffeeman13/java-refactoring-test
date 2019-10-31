package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;
import com.h2rd.refactoring.web.UserResource;
import java.util.Arrays;
import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

public class UserResourceUnitTest {

    UserResource userResource;
    UserDao userDao;

    /* clear all users after each test */
    @After
    public void clearUsers() {
        userDao = UserDao.getUserDao();
        if (userDao.getUsers() != null && userDao.getUsers().size() > 0) {
            userDao.getUsers().clear();
        }
    }

    /**
     * Unit test for getUsers
     */
    @Test
    public void getUsersTest() {

        userResource = new UserResource();
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));
        userDao.saveUser(user);

        Response response = userResource.getUsers();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    /**
     * Unit test for addUser. The user should be saved without errors
     */
    @Test
    public void addUserTest() {
        userResource = new UserResource();
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));
        userDao.saveUser(user);

        Response response = userResource.findUser("fake@email.com");
        Assert.assertEquals(user, response.getEntity());

    }

    /**
     * Unit test for updateUser. The user's name should be changed without error
     *
     */
    @Test
    public void updateUserTest() {

        userResource = new UserResource();
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));
        userDao.saveUser(user);

        /* update the user */
        user.setName("New Fake Name");
        Response response = userResource.updateUser(user.getName(), user.getEmail(), user.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        /* test if the change has been updated */
        User foundUser = userDao.findUser("fake@email.com");
        Assert.assertEquals(user.getName(), foundUser.getName());

    }

    /**
     * Unit test for deleteUser. The user should be deleted without errors
     */
    @Test
    public void deleteUserTest() {
        userResource = new UserResource();
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));
        userDao.saveUser(user);

        /* delete the user */
        Response response = userResource.deleteUser(user.getName(), user.getEmail(), user.getRoles());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        /* test if the user is deleted */
        User foundUser = userDao.findUser("fake@email.com");
        Assert.assertNull(foundUser);
    }

    /**
     * Unit test for findUser. If the user is in the list, it should be found
     * without errors
     */
    @Test
    public void findUserTest() {
        userResource = new UserResource();
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));
        userDao.saveUser(user);

        Response response = userResource.findUser("fake@email.com");
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Assert.assertEquals(user, (User) response.getEntity());

    }
}
