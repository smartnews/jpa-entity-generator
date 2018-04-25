package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.User")
@Table(name = "wp_users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"ID\"")
  private Long id;
  @Column(name = "\"user_login\"")
  private String userLogin;
  @Column(name = "\"user_pass\"")
  private String userPass;
  @Column(name = "\"user_nicename\"")
  private String userNicename;
  @Column(name = "\"user_email\"")
  private String userEmail;
  @Column(name = "\"user_url\"")
  private String userUrl;
  @Column(name = "\"user_registered\"")
  private Timestamp userRegistered;
  @Column(name = "\"user_activation_key\"")
  private String userActivationKey;
  @Column(name = "\"user_status\"")
  private Integer userStatus;
  @Column(name = "\"display_name\"")
  private String displayName;
}