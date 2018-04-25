package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.PostMeta")
@Table(name = "wp_postmeta")
public class PostMeta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"meta_id\"")
  private Long metaId;
  @Column(name = "\"post_id\"")
  private Long postId;
  @Column(name = "\"meta_key\"")
  private String metaKey;
  @Column(name = "\"meta_value\"")
  private String metaValue;

  @ManyToOne
  @JoinColumn(name = "\"post_id\"", insertable = false, updatable = false)
  private Post post;
}