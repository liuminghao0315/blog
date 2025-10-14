package com.lmh.blog_project.api;

import com.lmh.blog_project.common.Paging;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import com.lmh.blog_project.model.Blog;
import com.lmh.blog_project.model.BlogWithUserName;
import com.lmh.blog_project.model.User;
import com.lmh.blog_project.service.BlogService;
import com.lmh.blog_project.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class BlogWithUserNameApi {
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @RequestMapping("/bwun/findbyid")
    public BlogWithUserNameDTO findBlogById(String id) {
        Blog blog = blogService.findById(id);
        User user = userService.findById(blog.getAuthorId());
        return new BlogWithUserNameDTO(blog, user);
    }

    // 以下是根据排序/筛选进行查，并且是在数据库中联立查询，而不是在java中进行嵌套循环拼接，up代表升序，down代表降序
    // 发布时间
    @RequestMapping("/bwun/findSortByDate")
    public Result<Paging<BlogWithUserName>> findSortByDate(String sortFlag, String marchText, String myBlogUserId, String favoriteUserId, Integer pageNum, Integer pageSize){
        pageNum = pageNum==null?1:pageNum;
        pageSize = pageSize==null?10:pageSize;
        Result<Paging<BlogWithUserName>> result = blogService.findSortByDate(sortFlag, marchText, myBlogUserId, favoriteUserId, pageNum, pageSize);
        if(!result.getSuccess()){
            result.setData(null);
            return result;
        }
        return result;
    }

    // 点赞量
    @RequestMapping("/bwun/findSortByLike")
    public Result<Paging<BlogWithUserName>> findSortByLike(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId, Integer pageNum, Integer pageSize){
        pageNum = pageNum==null?1:pageNum;
        pageSize = pageSize==null?10:pageSize;
        Result<Paging<BlogWithUserName>> result = blogService.findSortByLike(sortFlag, marchText, myBlogUserId, favoriteUserId, pageNum, pageSize);
        if(!result.getSuccess()){
            result.setData(null);
            return result;
        }
        return result;
    }

    // 收藏量
    @RequestMapping("/bwun/findSortByFavorite")
    public Result<Paging<BlogWithUserName>> findSortByFavorite(String sortFlag, String marchText,String myBlogUserId,String favoriteUserId, Integer pageNum, Integer pageSize){
        pageNum = pageNum==null?1:pageNum;
        pageSize = pageSize==null?10:pageSize;
        Result<Paging<BlogWithUserName>> result = blogService.findSortByFavorite(sortFlag, marchText, myBlogUserId, favoriteUserId, pageNum, pageSize);
        if(!result.getSuccess()){
            result.setData(null);
            return result;
        }
        return result;
    }

    // 评论数
    @RequestMapping("/bwun/findSortByComment")
    public Result<Paging<BlogWithUserName>> findSortByComment(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId, Integer pageNum, Integer pageSize){
        pageNum = pageNum==null?1:pageNum;
        pageSize = pageSize==null?10:pageSize;
        Result<Paging<BlogWithUserName>> result = blogService.findSortByComment(sortFlag, marchText, myBlogUserId, favoriteUserId, pageNum, pageSize);
        if(!result.getSuccess()){
            result.setData(null);
            return result;
        }
        return result;
    }



    // 当前端Detail点击 上一篇文章 或 下一篇文章 的时候，返回的文章对象
    // 收藏量
    @RequestMapping("/bwun/findLastAndNextRelyOnFavoriteById")
    public Map<String, BlogWithUserName> findLastAndNextRelyOnFavoriteById(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId){
        return blogService.findSortedRelyOnFavorite(sortFlag, marchText, blogId,myBlogUserId,favoriteUserId);
    }

    // 点赞量
    @RequestMapping("/bwun/findLastAndNextRelyOnLikeById")
    public Map<String, BlogWithUserName> findLastAndNextRelyOnLikeById(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId){
        return blogService.findSortedRelyOnLike(sortFlag, marchText, blogId,myBlogUserId,favoriteUserId);
    }

    // 评论数
    @RequestMapping("/bwun/findLastAndNextRelyOnCommentById")
    public Map<String, BlogWithUserName> findLastAndNextRelyOnCommentById(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId){
        return blogService.findSortedRelyOnComment(sortFlag, marchText, blogId,myBlogUserId,favoriteUserId);
    }

    // 发布时间
    @RequestMapping("/bwun/findLastAndNextRelyOnDateById")
    public Map<String, BlogWithUserName> findLastAndNextRelyOnDateById(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId){
        return blogService.findSortedRelyOnDate(sortFlag, marchText, blogId,myBlogUserId,favoriteUserId);
    }
}
