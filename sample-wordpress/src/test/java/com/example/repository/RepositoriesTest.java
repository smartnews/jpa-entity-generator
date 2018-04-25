package com.example.repository;

import com.example.entity.Post;
import com.example.entity.PostMeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RepositoriesTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMetaRepository postMetaRepository;

    @Test
    public void testPosts() {
        Post p = new Post();
        p.setPostName("name");
        p.setCommentCount(0L);
        p.setCommentStatus("status");
        p.setGuid("guid");
        p.setMenuOrder(123);
        p.setPingStatus("ping status");
        p.setPinged("true");
        p.setPostAuthor(111L);
        p.setPostContent("content");
        p.setPostContentFiltered("filtered");
        p.setPostDate(new Timestamp(System.currentTimeMillis()));
        p.setPostDateGmt(new Timestamp(System.currentTimeMillis()));
        p.setPostMimeType("mime_type");
        p.setPostExcerpt("excerpt");
        p.setPostModified(new Timestamp(System.currentTimeMillis()));
        p.setPostModifiedGmt(new Timestamp(System.currentTimeMillis()));
        p.setPostParent(111L);
        p.setPostPassword("pass");
        p.setPostStatus("post status");
        p.setPostTitle("title");
        p.setPostType("type");
        p.setToPing("to ping");
        postRepository.save(p);

        PostMeta pm = new PostMeta();
        pm.setPostId(p.getId());
        pm.setMetaKey("foo");
        pm.setMetaValue("bar");
        postMetaRepository.save(pm);

        Optional<Post> foundPost = postRepository.findById(p.getId());
        assertThat(foundPost.isPresent(), is(true));
        foundPost.ifPresent(post -> {
            assertThat(post.getPostMetaList().size(), is(greaterThan(0)));
        });
        long count = postRepository.count();
        assertThat(count, is(greaterThan(0L)));
    }
}
