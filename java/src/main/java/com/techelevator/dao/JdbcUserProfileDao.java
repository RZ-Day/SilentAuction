package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import com.techelevator.model.RegisterUserDto;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.UserProfile;

@Component
public class JdbcUserProfileDao implements UserProfileDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserProfileDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserProfile getUserProfileById(int id) {
        if (id < 1) throw new IllegalArgumentException("User ID cannot be below 1");
        UserProfile user = null;
        String sql = "SELECT user_id, username, full_name, email, phone, address_billing, address_shipping, allow_anonymous FROM users " +
                "WHERE user_id = ?;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
            if (rowSet.next()) {
                user = mapRowToUserProfile(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public UserProfile getUserProfileByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");
        UserProfile user = null;
        String sql = "SELECT user_id, username, full_name, email, phone, address_billing, address_shipping, allow_anonymous FROM users " +
                "WHERE username = ?;";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if (rowSet.next()) {
                user = mapRowToUserProfile(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile) {
        String sql = "UPDATE users SET email = ?, phone = ?, address_billing = ?, " +
                     "address_shipping = ?, allow_anonymous = ? WHERE username = ?;";
        try {
            int rowCount = jdbcTemplate.update(sql, userProfile.getEmail(), userProfile.getPhone(),
                    userProfile.getBillingAddress(), userProfile.getShippingAddress(), userProfile.isAllowAnonymous(),
                    userProfile.getUsername());
            if (rowCount < 1)
                throw new DaoException("Expected row count was less than 1");
            return getUserProfileByUsername(userProfile.getUsername());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
    }

    private UserProfile mapRowToUserProfile(SqlRowSet rs) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(rs.getInt("user_id"));
        userProfile.setUsername(rs.getString("username"));
        userProfile.setFullName(rs.getString("full_name"));
        userProfile.setEmail(rs.getString("email"));
        userProfile.setPhone(rs.getString("phone"));
        userProfile.setBillingAddress(rs.getString("address_billing"));
        userProfile.setShippingAddress(rs.getString("address_shipping"));
        userProfile.setAllowAnonymous(rs.getBoolean("allow_anonymous"));
        return userProfile;
    }
}
