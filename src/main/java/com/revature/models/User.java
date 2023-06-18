package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @JoinColumn(name = "role_fk", referencedColumnName = "role_id")
  @ManyToOne(targetEntity = Role.class)
  private int roleID;

  public User() {
  }

  public User(int id, String firstName, String lastName, String username,
              String password, int roleID) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.roleID = roleID;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
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

  public int getRoleID() {
    return roleID;
  }

  public void setRoleID(int roleID) {
    this.roleID = roleID;
  }
}
