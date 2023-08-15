package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.UserProfileDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT user_id, full_name, email, phone, address, username, password_hash, role FROM users WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash, role FROM users";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                User user = mapRowToUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        User user = null;
        String sql = "SELECT user_id, username, full_name, email, phone, address_billing, address_shipping, role, allow_anonymous " +
                     "FROM users WHERE username = ?;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if (rowSet.next()) {
                user = mapRowToUser(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public void updateUserEmail(String username, String email) {
        String sql = "UPDATE users SET email = ? WHERE username =?";
        jdbcTemplate.update(sql, email, username);
    }

    @Override
    public void updateUserBillingAddress(String username, String billingAddress) {
        String sql = "UPDATE users SET address_billing = ? WHERE username =?";
        jdbcTemplate.update(sql, billingAddress, username);
    }
    @Override
    public void updateUserShippingAddress(String username, String shippingAddress) {
        String sql = "UPDATE users SET address_shipping = ? WHERE username =?";
        jdbcTemplate.update(sql, shippingAddress, username);
    }

    @Override
    public void updateUserPhone(String username, String phone) {
        int phoneNumber = 0;
        try {
            phoneNumber = Integer.parseInt(phone);
        } catch (NumberFormatException e) {
            // ya dun goofed kid
        }
        String sql = "UPDATE users SET phone = ? WHERE username =?";
        jdbcTemplate.update(sql, phoneNumber, username);
    }

    @Override
    public void updateAllowAnonymous(String username, boolean allowAnonymous) {
        String sql = "UPDATE users SET allow_anonymous = ? WHERE username = ?";
        jdbcTemplate.update(sql, allowAnonymous, username);
    }

    @Override
    public User createUser(RegisterUserDto user) {
        User newUser = null;
        String insertUserSql = "INSERT INTO users (full_name, email, phone, address, username, password_hash, role)" +
                                " values (?, ?, ?, ?, ?, ?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(user.getPassword());
        String ssRole = user.getRole().toUpperCase().startsWith("ROLE_") ? user.getRole().toUpperCase() : "ROLE_" + user.getRole().toUpperCase();
        try {
            System.out.println("GOT TO USERDAO");
            int newUserId = jdbcTemplate.queryForObject(insertUserSql,
                                                        int.class,
                                                        user.getName(),
                                                        user.getEmail(),
                                                        user.getPhone(),
                                                        user.getAddress(),
                                                        user.getUsername(),
                                                        password_hash,
                                                        ssRole);
            newUser = getUserById(newUserId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newUser;
    }

    private User mapRowToUser(SqlRowSet rs) {
        // full_name, email, phone, address_billing, address_shipping, role, allow_anonymous
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setBillingAddress(rs.getString("address_billing"));
        user.setShippingAddress(rs.getString("address_shipping"));
        user.setFullName(rs.getString("full_name"));
        user.setAllowAnonymous(rs.getBoolean("allow_anonymous"));

        return user;
    }
}
