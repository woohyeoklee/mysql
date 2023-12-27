package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPost;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCommand;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPost> getDailyPostCount(DailyPostCommand command) {
        /*
        [일자, 회원, 게시글수]를 조회하는 쿼리를 작성해주세요.
        */
        return postRepository.getCount(command);
    }
}
