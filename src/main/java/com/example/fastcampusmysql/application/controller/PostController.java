package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.DailyPost;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCommand;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostWriteService postWriteService;
    private final PostReadService postReadService;

    @PostMapping("")
    public Long createPost(PostCommand command) {
        return postWriteService.createPost(command);
    }

    @GetMapping("/daily-posts")
    public List<DailyPost> getDailyPostCount(DailyPostCommand command) {
        return postReadService.getDailyPostCount(command);
    }

    @GetMapping("/members/{memberId}")
    public Page<Post> getPost(
            @PathVariable Long memberId, Pageable pageable
    ) {

        return postReadService.getPosts(memberId, pageable);
    }
}
