package com.h2rd.refactoring.usermanagement;

import java.util.ArrayList;
import java.util.Iterator;

/* Since email is the unique identifier,
* changed all comparisons to user's name instead of name
 */
public class UserDao {

    public ArrayList<User> users;

    public static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public boolean saveUser(User user) {
        if (users == null) {
            users = new ArrayList<User>();
        }

        /* a simple check to see if a user has at least
        one role
         */
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            System.out.println("User should have at least one role");
            return false;
        } else {
            users.add(user);
            return true;
        }
    }

    public ArrayList<User> getUsers() {
        try {
            return users;
        } catch (Throwable e) {
            System.out.println("error");
            return null;
        }
    }

    public void deleteUser(User userToDelete) {
        try {

            /* changed the code to handle ConcurrentModificationException */
            for (Iterator<User> itr = users.iterator(); itr.hasNext();) {
                User user = itr.next();
                if (user.getEmail().equals(userToDelete.getEmail())) {
                    itr.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* since user's email is now the basis for comparison,
     * user's name will be modified instead
     */
    public void updateUser(User userToUpdate) {
        try {
            for (User user : users) {
                if (user.getEmail().equals(userToUpdate.getEmail())) {
                    user.setName(userToUpdate.getName());
                    user.setRoles(userToUpdate.getRoles());
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /* changed the parameter to user's email */
    public User findUser(String email) {
        try {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
