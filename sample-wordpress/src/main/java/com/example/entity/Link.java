package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.Link")
@Table(name = "wp_links")
public class Link {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"link_id\"")
  private Long linkId;
  @Column(name = "\"link_url\"")
  private String linkUrl;
  @Column(name = "\"link_name\"")
  private String linkName;
  @Column(name = "\"link_image\"")
  private String linkImage;
  @Column(name = "\"link_target\"")
  private String linkTarget;
  @Column(name = "\"link_description\"")
  private String linkDescription;
  @Column(name = "\"link_visible\"")
  private String linkVisible;
  @Column(name = "\"link_owner\"")
  private Long linkOwner;
  @Column(name = "\"link_rating\"")
  private Integer linkRating;
  @Column(name = "\"link_updated\"")
  private Timestamp linkUpdated;
  @Column(name = "\"link_rel\"")
  private String linkRel;
  @Column(name = "\"link_notes\"")
  private String linkNotes;
  @Column(name = "\"link_rss\"")
  private String linkRss;
}