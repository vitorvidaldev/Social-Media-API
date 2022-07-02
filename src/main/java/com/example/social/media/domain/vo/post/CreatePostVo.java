package com.example.social.media.domain.vo.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class CreatePostVo {
    @Size(max = 777)
    private String content;
    private boolean isRepost;
    @Size(max = 777)
    private String quote;

    public CreatePostVo() {
    }

    public CreatePostVo(String content) {
        this.content = content;
        this.isRepost = false;
    }

    public CreatePostVo(String content, boolean isRepost, String quote) {
        this.content = content;
        this.isRepost = isRepost;
        this.quote = quote;
    }
}
