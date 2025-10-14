package com.lmh.blog_project.api;

import com.lmh.blog_project.dao.FavoriteDAO;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import com.lmh.blog_project.dto.FavoriteDTO;
import com.lmh.blog_project.model.Favorite;
import com.lmh.blog_project.service.BlogService;
import com.lmh.blog_project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class FavoriteApi {
    @Autowired
    private FavoriteDAO favoriteDAO;
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private BlogService blogService;


    //在Detail.vue中调用
    @RequestMapping("/db/favorite/add")
    public Integer add(@RequestBody Favorite favorite){
        return favoriteService.add(favorite);
    }

    //在UserSpace.vue中调用
    @RequestMapping("/db/favorite/delete")
    public Integer delete(String userId,String articleId){
        return favoriteService.delete(userId, articleId);
    }

    //在Detail.vue中调用
    @RequestMapping("/db/favorite/deleteByArticleId")
    public Integer deleteByArticleId(String articleId){
        return favoriteService.deleteByArticleId(articleId);
    }

    //在Detail.vue中调用
    @RequestMapping("/db/favorite/getDTO")
    public FavoriteDTO getDTO(String userId, String articleId){
        return favoriteService.assembleFavoriteDTO(userId,articleId);
    }

//    @RequestMapping("/test")
//    public void test(String userId){
//        List<String> result = favoriteService.findArticleIdsByUserId(userId);
//        try {
//            System.out.println(new ObjectMapper().writeValueAsString(result));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
