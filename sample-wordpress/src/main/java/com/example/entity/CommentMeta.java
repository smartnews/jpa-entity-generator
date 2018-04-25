package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.CommentMeta")
@Table(name = "wp_commentmeta")
public class CommentMeta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"meta_id\"")
  private Long metaId;
  @Column(name = "\"comment_id\"")
  private Long commentId;
  @Column(name = "\"meta_key\"")
  private String metaKey;
  @Column(name = "\"meta_value\"")
  private String metaValue;
}