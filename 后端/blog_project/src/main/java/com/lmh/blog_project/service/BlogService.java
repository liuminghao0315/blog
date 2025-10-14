package com.lmh.blog_project.service;

import com.lmh.blog_project.common.Paging;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.dataobject.BlogDO;
import com.lmh.blog_project.dataobject.BlogWithUserNameDO;
import com.lmh.blog_project.dto.BlogDTO;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import com.lmh.blog_project.model.Blog;
import com.lmh.blog_project.model.BlogWithUserName;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BlogService {
    // 增
    Integer add(BlogWithUserNameDTO blogWithUserNameDTO);

    // 删
    Integer delete(String id);

    // 查——全部
    Result<Paging<Blog>> findAll(Integer pageNum, Integer pageSize);
    // 查——单个
    Blog findById(String id);
    // 查——若干个
    Result<Paging<Blog>> findByIds(List<String> blogIds,Integer pageNum, Integer pageSize);

    // 改
    Integer update(Blog blog);

    // 以下是根据排序/筛选进行查，并且是在数据库中联立查询，而不是在java中进行嵌套循环拼接，up代表升序，down代表降序
    // 发布时间
    Result<Paging<BlogWithUserName>> findSortByDate(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize);
    // 点赞量
    Result<Paging<BlogWithUserName>> findSortByLike(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize);
    // 收藏量
    Result<Paging<BlogWithUserName>> findSortByFavorite(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize);
    // 评论数
    Result<Paging<BlogWithUserName>> findSortByComment(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize);

    // 当前端Detail点击 上一篇文章 或 下一篇文章 的时候，返回的文章对象
    // 收藏量
    Map<String,BlogWithUserName> findSortedRelyOnFavorite(String sortFlag,String marchText,String blogId,String myBlogUserId,String favoriteUserId);

    //点赞量
    Map<String,BlogWithUserName> findSortedRelyOnLike(String sortFlag,String marchText,String blogId,String myBlogUserId,String favoriteUserId);

    //评论量
    Map<String,BlogWithUserName> findSortedRelyOnComment(String sortFlag,String marchText,String blogId,String myBlogUserId,String favoriteUserId);

    //发布时间
    Map<String,BlogWithUserName> findSortedRelyOnDate(String sortFlag,String marchText,String blogId,String myBlogUserId,String favoriteUserId);
}
