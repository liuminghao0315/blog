package com.lmh.blog_project.dto;

import java.io.Serializable;

public class FavoriteDTO implements Serializable {
    private static final long serialVersionUID = -1897702023106962305L;
    private Integer favoriteCount;
    private Boolean ifUserFavorite;

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Boolean getIfUserFavorite() {
        return ifUserFavorite;
    }

    public void setIfUserFavorite(Boolean ifUserFavorite) {
        this.ifUserFavorite = ifUserFavorite;
    }
}
