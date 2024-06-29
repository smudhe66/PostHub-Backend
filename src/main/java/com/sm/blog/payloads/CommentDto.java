package com.sm.blog.payloads;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CommentDto {

	private Integer id;

    private String content;
}
