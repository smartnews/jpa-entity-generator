package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.Ec3Schedule")
@Table(name = "wp_ec3_schedule")
public class Ec3Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"sched_id\"")
  private Long schedId;
  @Column(name = "\"post_id\"")
  private Long postId;
  @Column(name = "\"start\"")
  private Timestamp start;
  @Column(name = "\"end\"")
  private Timestamp end;
  @Column(name = "\"allday\"")
  private Byte allday;
  @Column(name = "\"rpt\"")
  private String rpt;
}