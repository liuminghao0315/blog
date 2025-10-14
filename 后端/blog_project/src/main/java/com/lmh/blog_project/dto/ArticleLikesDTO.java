package com.lmh.blog_project.dto;

import java.io.Serializable;

public class ArticleLikesDTO implements Serializable {
    private static final long serialVersionUID = -3417884754351812328L;
    private Integer likesNum;
    private Integer myIncrement;

    public Integer getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(Integer likesNum) {
        this.likesNum = likesNum;
    }

    public Integer getMyIncrement() {
        return myIncrement;
    }

    public void setMyIncrement(Integer myIncrement) {
        this.myIncrement = myIncrement;
    }
}
