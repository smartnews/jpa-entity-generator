package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.TermRelationship")
@Table(name = "wp_term_relationships")
@IdClass(TermRelationship.PrimaryKeys.class)
public class TermRelationship {
  @Data
  public static class PrimaryKeys implements Serializable {
    private Long objectId;
    private Long termTaxonomyId;
  }

  @Id
  @Column(name = "\"object_id\"")
  private Long objectId;
  @Id
  @Column(name = "\"term_taxonomy_id\"")
  private Long termTaxonomyId;
  @Column(name = "\"term_order\"")
  private Integer termOrder;
}