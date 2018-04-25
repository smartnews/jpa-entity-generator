package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.Term")
@Table(name = "wp_terms")
public class Term {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"term_id\"")
  private Long termId;
  @Column(name = "\"name\"")
  private String name;
  @Column(name = "\"slug\"")
  private String slug;
  @Column(name = "\"term_group\"")
  private Long termGroup;
}