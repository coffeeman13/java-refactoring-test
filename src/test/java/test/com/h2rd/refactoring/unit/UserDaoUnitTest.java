package test.com.h2rd.refactoring.unit;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;
import java.util.Arrays;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class UserDaoUnitTest {

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
     * Unit test for saveUser. The user should be saved without errors
     */
    @Test
    public void saveUserTest() {
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));

        userDao.saveUser(user);

        Assert.assertNotNull(userDao.findUser("fake@email.com"));

        /* if the user has no role, it should not be saved */
        User newUser = new User();
        newUser.setName("Test Name");
        newUser.setEmail("test@email.com");

        userDao.saveUser(newUser);
        Assert.assertNull(userDao.findUser("test@email.com"));

    }

    /**
     * Unit test for deleteUser. The user should be deleted without errors
     */
    @Test
    public void deleteUserTest() {
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));

        userDao.saveUser(user);

        try {
            userDao.deleteUser(user);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Assert.assertNull(userDao.findUser("fake@email.com"));
    }

    /**
     * Unit test for getUserDao. The userDao should not be null
     */
    @Test
    public void getUserDaoTest() {

        Assert.assertNotNull(UserDao.getUserDao());

    }

    /**
     * Unit test for getUsers. The list should only have one item
     */
    @Test
    public void getUsersTest() {
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));

        userDao.saveUser(user);

        /* test if user list will have the same number of users as expected */
        Assert.assertEquals(1, userDao.getUsers().size());
    }

    /**
     * Unit test for updateUser. The user's name should be changed without error
     *
     */
    @Test
    public void updateUserTest() {
        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));

        userDao.saveUser(user);

        /* updating the user */
        user.setName("Updated Fake Name");
        userDao.updateUser(user);

        /* test if user has been updated */
        User updatedUser = userDao.findUser("fake@email.com");
        Assert.assertEquals(user.getName(), updatedUser.getName());

    }

    /**
     * Unit test for findUser. If the user is in the list, it should be found
     * without errors
     */
    @Test
    public void findUserTest() {

        userDao = UserDao.getUserDao();

        User user = new User();
        user.setName("Fake Name");
        user.setEmail("fake@email.com");
        user.setRoles(Arrays.asList("admin", "master"));

        userDao.saveUser(user);

        User foundUser = userDao.findUser("fake@email.com");

        /* test if user is on the list */
        Assert.assertEquals(user, foundUser);

        /* test if user is not on the list; should be null */
        Assert.assertNull(userDao.findUser("test@email.com"));
    }
}
