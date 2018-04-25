package com.example.repository;

import com.example.entity.PostMeta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMetaRepository extends CrudRepository<PostMeta, Long> {
}
