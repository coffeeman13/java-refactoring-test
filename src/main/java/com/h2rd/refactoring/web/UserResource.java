package com.h2rd.refactoring.web;

import com.h2rd.refactoring.usermanagement.User;
import com.h2rd.refactoring.usermanagement.UserDao;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Repository;

/* Updated to the correct HTTP request methods */
@Path("/users")
@Repository
public class UserResource {

    public UserDao userDao;

    @POST
    @Path("/add")
    public Response addUser(@QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("role") List<String> roles) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);

        if (userDao == null) {
            userDao = UserDao.getUserDao();
        }

        userDao.saveUser(user);
        return Response.ok().entity(user).build();
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("role") List<String> roles) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);

        if (userDao == null) {
            userDao = UserDao.getUserDao();
        }

        userDao.updateUser(user);
        return Response.ok().entity(user).build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteUser(@QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("role") List<String> roles) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(roles);

        if (userDao == null) {
            userDao = UserDao.getUserDao();
        }

        userDao.deleteUser(user);
        return Response.ok().entity(user).build();
    }

    @GET
    @Path("/find")
    public Response getUsers() {

        if (userDao == null) {
            userDao = UserDao.getUserDao();
        }
        List<User> users = userDao.getUsers();
        if (users == null) {
            users = new ArrayList<User>();
        }

        GenericEntity<List<User>> usersEntity = new GenericEntity<List<User>>(users) {
        };
        return Response.status(200).entity(usersEntity).build();
    }

    /* changed the parameter to email */
    @GET
    @Path("/search")
    public Response findUser(@QueryParam("email") String email) {

        if (userDao == null) {
            userDao = UserDao.getUserDao();
        }

        User user = userDao.findUser(email);
        return Response.ok().entity(user).build();

    }
}
