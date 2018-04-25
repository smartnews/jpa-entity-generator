package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.Comment")
@Table(name = "wp_comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"comment_ID\"")
  private Long commentId;
  @Column(name = "\"comment_post_ID\"")
  private Long commentPostId;
  @Column(name = "\"comment_author\"")
  private String commentAuthor;
  @Column(name = "\"comment_author_email\"")
  private String commentAuthorEmail;
  @Column(name = "\"comment_author_url\"")
  private String commentAuthorUrl;
  @Column(name = "\"comment_author_IP\"")
  private String commentAuthorIp;
  @Column(name = "\"comment_date\"")
  private Timestamp commentDate;
  @Column(name = "\"comment_date_gmt\"")
  private Timestamp commentDateGmt;
  @Column(name = "\"comment_content\"")
  private String commentContent;
  @Column(name = "\"comment_karma\"")
  private Integer commentKarma;
  @Column(name = "\"comment_approved\"")
  private String commentApproved;
  @Column(name = "\"comment_agent\"")
  private String commentAgent;
  @Column(name = "\"comment_type\"")
  private String commentType;
  @Column(name = "\"comment_parent\"")
  private Long commentParent;
  @Column(name = "\"user_id\"")
  private Long userId;
}