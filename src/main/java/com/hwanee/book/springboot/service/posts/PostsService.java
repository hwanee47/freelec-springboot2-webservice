package com.hwanee.book.springboot.service.posts;

import com.hwanee.book.springboot.domain.posts.Posts;
import com.hwanee.book.springboot.domain.posts.PostsRepository;
import com.hwanee.book.springboot.web.dto.PostListResponseDto;
import com.hwanee.book.springboot.web.dto.PostsResponseDto;
import com.hwanee.book.springboot.web.dto.PostsSaveRequestDto;
import com.hwanee.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto) {
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 수정처리
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }


    public PostsResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseDto> findAllDesc(){
//        return postsRepository.findAllDesc().stream().map(p -> new PostListResponseDto(p)).collect(Collectors.toList());
        return postsRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }
}
