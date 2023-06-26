package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "reimbursements")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reimbursement {
  @Id
  @Column(name = "reimb_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private int amount;

  @Column(nullable = false)
  private String description;

  @ManyToOne(targetEntity = User.class)
  private User user;

  @ManyToOne(targetEntity = Status.class)
  private Status status;
}
