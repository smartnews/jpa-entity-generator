package com.example.entity5;

import java.io.Serializable;
import java.sql.*;
import javax.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Note: auto-generated by jpa-entity-generator
 */
@Data
@ToString
@Builder(toBuilder = true)
@Entity(name = "com.example.entity5.Blog")
@Table(name = "blog")
public class Blog implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"id\"", nullable = false)
  private Integer id;
  @Column(name = "\"name\"", nullable = true)
  private String name;
  @Column(name = "\"active\"", nullable = true)
  private boolean active;
  @Column(name = "\"created_at\"", nullable = false)
  private Timestamp createdAt;
  @OneToMany(mappedBy = "blog")
  private java.util.List<BlogArticle> listOfBlogArticle;
}