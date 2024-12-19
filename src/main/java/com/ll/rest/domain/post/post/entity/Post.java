package com.ll.rest.domain.post.post.entity;

import com.ll.rest.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTime { //BaseTime이 BaseEntity도 상속 받음
    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
}
