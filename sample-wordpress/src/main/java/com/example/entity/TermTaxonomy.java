package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.TermTaxonomy")
@Table(name = "wp_term_taxonomy")
public class TermTaxonomy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"term_taxonomy_id\"")
  private Long termTaxonomyId;
  @Column(name = "\"term_id\"")
  private Long termId;
  @Column(name = "\"taxonomy\"")
  private String taxonomy;
  @Column(name = "\"description\"")
  private String description;
  @Column(name = "\"parent\"")
  private Long parent;
  @Column(name = "\"count\"")
  private Long count;
}