package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.dto.PostDto;
import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import com.ll.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
	private final PostService postService;

	@GetMapping
	public List<PostDto> getItems() {
		return postService
				.findAllByOrderByIdDesc()
				.stream()
				.map(PostDto::new)
				.toList();
	}


	@GetMapping("/{id}")
	public PostDto getItem(
			@PathVariable long id
	) {
		return postService.findById(id)
				.map(PostDto::new) //Dto로 변환
				.orElseThrow();
	}


	@DeleteMapping("/{id}")
	public RsData deleteItem(
			@PathVariable long id
	) {
		Post post = postService.findById(id).get();

		postService.delete(post);

		return new RsData(
				"200-1",
				"%d번 글을 삭제하였습니다.".formatted(id)
		);
	}

	/*
	@AllArgsConstructor
	@Getter
	public static class PostModifyReqBody {
		private String title;
		private String content;
	}

	 */

	//코딩량을 줄이기 위한 것
	record PostModifyReqBody(
			@NotBlank
			@Length(min = 2)
			String title,
			@NotBlank
			@Length(min = 2)
			String content
	) {
	}

	@PutMapping("/{id}")
	@Transactional
	public RsData<PostDto> modifyItem(
			@PathVariable long id,
			@RequestBody PostModifyReqBody reqBody
	) {
		Post post = postService.findById(id).get();

		postService.modify(post, reqBody.title, reqBody.content);

		return new RsData<>(
				"200-1",
				"%d번 글이 수정되었습니다.".formatted(id),
				new PostDto(post)
		);
	}

	record PostWriteReqBody(
			@NotBlank
			@Length(min = 2)
			String title,
			@NotBlank
			@Length(min = 2)
			String content
	) {
	}

	@PostMapping
	public RsData<Long> writeItem(
			@RequestBody @Valid PostWriteReqBody reqBody
	) {
		Post post = postService.write(reqBody.title, reqBody.content);

		return new RsData<>(
				"200-1",
				"%d번 글이 작성되었습니다.".formatted(post.getId()),
				post.getId()
		);
	}
}