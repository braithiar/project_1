package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "reimbursements")
public class Reimbursement {
  @Id
  @Column(name = "reimb_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private int amount;

  @Column(nullable = false)
  private String description;

  @JoinColumn(name = "user_fk", referencedColumnName = "user_id", nullable = false)
  @ManyToOne(targetEntity = User.class)
  private int userID;

  // TODO: update reimbursement model to contain a Status object instead of sid
  @JoinColumn(name = "status_fk", referencedColumnName = "status_id", nullable = false)
  @ManyToOne(targetEntity = Status.class)
  private int statusID;

  public Reimbursement() {
  }

  public Reimbursement(int id, int amount, String description, int userID,
                       int statusID) {
    this.id = id;
    this.amount = amount;
    this.description = description;
    this.userID = userID;
    this.statusID = statusID;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public int getStatusID() {
    return statusID;
  }

  public void setStatusID(int statusID) {
    this.statusID = statusID;
  }
}
