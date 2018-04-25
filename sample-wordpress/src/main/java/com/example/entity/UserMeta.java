package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.UserMeta")
@Table(name = "wp_usermeta")
public class UserMeta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"umeta_id\"")
  private Long umetaId;
  @Column(name = "\"user_id\"")
  private Long userId;
  @Column(name = "\"meta_key\"")
  private String metaKey;
  @Column(name = "\"meta_value\"")
  private String metaValue;
}