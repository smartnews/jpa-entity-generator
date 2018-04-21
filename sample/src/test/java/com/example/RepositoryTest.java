package com.example;

import com.example.entity.Blog;
import com.example.repository.BlogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    @Test
    public void ensureEntities() {
        {
            long numOfBlogs = blogRepository.count();
            assertThat(numOfBlogs, is(0L));
        }
        Blog b1 = new Blog();
        b1.setName("First-ever Blog");
        blogRepository.save(b1);
        blogRepository.save(b1.toBuilder().name("Day 1 Blog").build());
        blogRepository.save(Blog.builder().name("Second Life").build());
        {
            long numOfBlogs = blogRepository.count();
            assertThat(numOfBlogs, is(2L));
        }
    }

}
