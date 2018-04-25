package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.Post")
@Table(name = "wp_posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"ID\"")
  private Long id;
  @Column(name = "\"post_author\"")
  private Long postAuthor;
  @Column(name = "\"post_date\"")
  private Timestamp postDate;
  @Column(name = "\"post_date_gmt\"")
  private Timestamp postDateGmt;
  @Column(name = "\"post_content\"")
  private String postContent;
  @Column(name = "\"post_title\"")
  private String postTitle;
  @Column(name = "\"post_excerpt\"")
  private String postExcerpt;
  @Column(name = "\"post_status\"")
  private String postStatus;
  @Column(name = "\"comment_status\"")
  private String commentStatus;
  @Column(name = "\"ping_status\"")
  private String pingStatus;
  @Column(name = "\"post_password\"")
  private String postPassword;
  @Column(name = "\"post_name\"")
  private String postName;
  @Column(name = "\"to_ping\"")
  private String toPing;
  @Column(name = "\"pinged\"")
  private String pinged;
  @Column(name = "\"post_modified\"")
  private Timestamp postModified;
  @Column(name = "\"post_modified_gmt\"")
  private Timestamp postModifiedGmt;
  @Column(name = "\"post_content_filtered\"")
  private String postContentFiltered;
  @Column(name = "\"post_parent\"")
  private Long postParent;
  @Column(name = "\"guid\"")
  private String guid;
  @Column(name = "\"menu_order\"")
  private Integer menuOrder;
  @Column(name = "\"post_type\"")
  private String postType;
  @Column(name = "\"post_mime_type\"")
  private String postMimeType;
  @Column(name = "\"comment_count\"")
  private Long commentCount;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL)
  private java.util.List<PostMeta> postMetaList;
}