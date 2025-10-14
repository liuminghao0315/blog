package com.lmh.blog_project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmh.blog_project.common.Paging;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.dao.FavoriteDAO;
import com.lmh.blog_project.dataobject.FavoriteDO;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import com.lmh.blog_project.dto.FavoriteDTO;
import com.lmh.blog_project.model.Favorite;
import com.lmh.blog_project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDAO favoriteDAO;

    @Override
    public Integer add(Favorite favorite) {
        Integer result = favoriteDAO.add(new FavoriteDO(favorite));
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer delete(String userId, String articleId) {
        Integer result = favoriteDAO.delete(userId, articleId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer deleteByArticleId(String articleId) {
        Integer result = favoriteDAO.deleteByArticleId(articleId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public List<String> findArticleIdsByUserId(String userId) {
        List<String> result = favoriteDAO.findArticleIdsByUserId(userId);
        return Objects.requireNonNullElse(result, null);
    }

    @Override
    public Result<Paging<BlogWithUserNameDTO>> findByUserId(String userId, Integer pageNum, Integer pageSize) {
        Page<BlogWithUserNameDTO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() ->favoriteDAO.findByUserId(userId));
        if(page.getResult()==null) {
            return new Result<>(false,"-","-",null);
        }
        return new Result<>(true, new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult()));
    }

    @Override
    public Integer findFavoriteCountByArticleId(String articleId) {
//        System.out.println("articleId:"+articleId);
        Integer result = favoriteDAO.findFavoriteCountByArticleId(articleId);
//        System.out.println("result:"+result);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public Integer findIfUserFavorite(String userId, String articleId) {
        Integer result = favoriteDAO.findIfUserFavorite(userId,articleId);
        return Objects.requireNonNullElse(result, 0);
    }

    @Override
    public FavoriteDTO assembleFavoriteDTO(String userId, String articleId) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setFavoriteCount(findFavoriteCountByArticleId(articleId));
        favoriteDTO.setIfUserFavorite(findIfUserFavorite(userId, articleId) > 0);
        return favoriteDTO;
    }


}
