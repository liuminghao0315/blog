package com.lmh.blog_project.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum BlogsImagesStatus {
    SIDEBAR_ONLY(0),
    ARTICLE_ONLY(1),
    BOTH(2);

    @EnumValue
    private final Integer statusNum;

    BlogsImagesStatus(Integer statusNum) {
        this.statusNum = statusNum;
    }
}
