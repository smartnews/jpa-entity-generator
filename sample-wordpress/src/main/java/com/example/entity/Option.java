package com.example.entity;

import java.sql.*;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity(name = "com.example.entity.Option")
@Table(name = "wp_options")
public class Option {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"option_id\"")
  private Long optionId;
  @Column(name = "\"blog_id\"")
  private Integer blogId;
  @Column(name = "\"option_name\"")
  private String optionName;
  @Column(name = "\"option_value\"")
  private String optionValue;
  @Column(name = "\"autoload\"")
  private String autoload;
}