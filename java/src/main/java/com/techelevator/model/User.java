package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

   // full_name, email, phone, address_billing, address_shipping, role, allow_anonymous

   private int userId;
   private String username;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();
   private boolean allowAnonymous;
   private String billingAddress;
   private String shippingAddress;

   private String phone;
   private String fullName;

   private String email;
   public User() { }

   public User(int userId, String username, String password, String authorities) {
      this.userId = userId;
      this.username = username;
      this.password = password;
      if (authorities != null) this.setAuthorities(authorities);
      this.activated = true;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFullName() {
      return this.fullName;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for (String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
         this.authorities.add(new Authority(authority));
      }
   }
   public String getBillingAddress() {
      return billingAddress;
   }

   public void setBillingAddress(String billingAddress) {
      this.billingAddress = billingAddress;
   }

   public String getShippingAddress() {
      return shippingAddress;
   }

   public void setShippingAddress(String shippingAddress) {
      this.shippingAddress = shippingAddress;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }
   public boolean isAllowAnonymous() {
      return allowAnonymous;
   }

   public void setAllowAnonymous(boolean allowAnonymous) {
      this.allowAnonymous = allowAnonymous;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return userId == user.userId &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(userId, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + userId +
              ", username='" + username + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
