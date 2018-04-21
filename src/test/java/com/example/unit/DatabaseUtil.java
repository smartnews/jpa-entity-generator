package com.example.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    public static void init() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./sample/db/blog;MODE=MySQL;TRACE_LEVEL_FILE=2;TRACE_LEVEL_SYSTEM_OUT=2", "user", "pass")) {
            conn.prepareStatement("drop table blog if exists").execute();
            conn.prepareStatement("drop table article if exists").execute();
            conn.prepareStatement("drop table tag if exists").execute();
            conn.prepareStatement("drop table article_tag if exists").execute();
            conn.prepareStatement("drop table abtest if exists").execute();
            conn.prepareStatement("create table blog (" +
                    "id integer primary key auto_increment not null, " +
                    "name varchar(30), " +
                    "active tinyint default 0, " +
                    "created_at timestamp not null" +
                    ")").execute();
            conn.prepareStatement("create table article (" +
                    "id integer primary key auto_increment not null, " +
                    "blog_id integer comment 'database comment for blog_id' references blog(id), " +
                    "name varchar(30), tags text, " +
                    "created_at timestamp not null" +
                    ")").execute();
            conn.prepareStatement("create table tag (" +
                    "id integer primary key auto_increment not null, " +
                    "tag varchar(100), " +
                    "created_at timestamp not null" +
                    ")").execute();
            conn.prepareStatement("create table article_tag (" +
                    "id integer primary key auto_increment not null, " +
                    "article_id integer not null comment 'database comment for article_id' references article(id), " +
                    "tag_id integer not null comment 'database comment for blog_id' references tag(id), " +
                    "created_at timestamp not null" +
                    ")").execute();
            conn.prepareStatement("create table abtest (" +
                    "identifier varchar(50) primary key not null, " +
                    "expiration_timestamp integer not null, " +
                    "config text" +
                    ")").execute();
        }

    }
}
