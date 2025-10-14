package com.lmh.blog_project.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmh.blog_project.common.Paging;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.dao.BlogDAO;
import com.lmh.blog_project.dataobject.BlogDO;
import com.lmh.blog_project.dataobject.BlogWithUserNameDO;
import com.lmh.blog_project.dto.BlogDTO;
import com.lmh.blog_project.dto.BlogWithUserNameDTO;
import com.lmh.blog_project.model.Blog;
import com.lmh.blog_project.model.BlogWithUserName;
import com.lmh.blog_project.model.User;
import com.lmh.blog_project.service.BlogService;
import com.lmh.blog_project.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    private UserService userService;

    @Override
    public Integer add(BlogWithUserNameDTO blogWithUserNameDTO) {
        Map<String, Object> DOAndUserName = blogWithUserNameDTO.toDOAndUserName();
        BlogDO blogDO = (BlogDO)(DOAndUserName.get("blogDO"));
        String userName = (String)(DOAndUserName.get("userName"));
        User user = userService.findByName(userName);
        String userId = user.getId();
        blogDO.setAuthorId(userId);
        return blogDAO.add(blogDO);
    }

    @Override
    public Integer delete(String id) {
        return blogDAO.delete(id);
    }

    @Override
    public Result<Paging<Blog>> findAll(Integer pageNum, Integer pageSize) {
        Page<BlogDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> blogDAO.findAll());
        return new Result<>(true, new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult().stream().map(BlogDO::toModel).collect(Collectors.toList())));
    }

    @Override
    public Blog findById(String id) {
        return blogDAO.findById(id).toModel();
    }

    @Override
    public Integer update(Blog blog) {
        return blogDAO.update(new BlogDO(blog));
    }

    @Override
    public Result<Paging<Blog>> findByIds(List<String> blogIds,Integer pageNum, Integer pageSize) {
        Page<BlogDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> blogDAO.findByIds(blogIds));
        return new Result<>(true, new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult().stream().map(BlogDO::toModel).collect(Collectors.toList())));
    }


    // 以下是根据排序/筛选进行查，并且是在数据库中联立查询，而不是在java中进行嵌套循环拼接，up代表升序，down代表降序
    // 发布时间
    @Override
    public Result<Paging<BlogWithUserName>> findSortByDate(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize){
        Page<BlogWithUserNameDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() ->blogDAO.findSortByDate(sortFlag,marchText,myBlogUserId,favoriteUserId));
        if(page.getResult()==null) {
            return new Result<>(false,"-","-",null);
        }
        return new Result<>(true, new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult().stream().map(BlogWithUserNameDO::toModel).collect(Collectors.toList())));
    }

    // 点赞量
    @Override
    public Result<Paging<BlogWithUserName>> findSortByLike(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize){
        Page<BlogWithUserNameDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() ->blogDAO.findSortByLike(sortFlag,marchText,myBlogUserId,favoriteUserId));
        if(page.getResult()==null) {
            return new Result<>(false,"-","-",null);
        }
        return new Result<>(true, new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult().stream().map(BlogWithUserNameDO::toModel).collect(Collectors.toList())));
    }

    // 收藏量
    @Override
    public Result<Paging<BlogWithUserName>> findSortByFavorite(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize){
        Page<BlogWithUserNameDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() ->blogDAO.findSortByFavorite(sortFlag,marchText,myBlogUserId,favoriteUserId));
        if(page.getResult()==null) {
            return new Result<>(false,"-","-",null);
        }
        return new Result<>(true, new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult().stream().map(BlogWithUserNameDO::toModel).collect(Collectors.toList())));
    }


    // 评论数
    @Override
    public Result<Paging<BlogWithUserName>> findSortByComment(String sortFlag,String marchText,String myBlogUserId,String favoriteUserId,Integer pageNum, Integer pageSize){
        Page<BlogWithUserNameDO> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(() ->blogDAO.findSortByComment(sortFlag,marchText,myBlogUserId,favoriteUserId));
        if(page.getResult()==null) {
            return new Result<>(false,"-","-",null);
        }
        return new Result<>(true, new Paging<>(page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal(), page.getResult().stream().map(BlogWithUserNameDO::toModel).collect(Collectors.toList())));
    }


    // 当前端Detail点击 上一篇文章 或 下一篇文章 的时候，返回的文章对象

    @Override
    public Map<String, BlogWithUserName> findSortedRelyOnFavorite(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId) {
        List<String> list = blogDAO.findSortedIdsRelyOnFavorite(sortFlag, marchText,myBlogUserId,favoriteUserId);
        if(list==null) {
            return null;
        }
        Map<String, BlogWithUserName> map = new HashMap<>();
        if(list.size()<=1) {
            map.put("last", null);
            map.put("next", null);
            return map;
        }
        for(int i=0; i<list.size(); i++) {
            String id = list.get(i);
            if(id.equals(blogId)) {
                if(i==0){
                    map.put("last", null);
                    BlogWithUserNameDO next = blogDAO.findSortedRelyOnFavoriteById(sortFlag, marchText, list.get(i+1));
                    if(next==null) {
                        map.put("next", null);
                    }else{
                        map.put("next", next.toModel());
                    }
                    return map;
                }
                if(i==list.size()-1){
                    map.put("next", null);
                    BlogWithUserNameDO last = blogDAO.findSortedRelyOnFavoriteById(sortFlag, marchText, list.get(i-1));
                    if(last==null) {
                        map.put("last", null);
                    }else{
                        map.put("last", last.toModel());
                    }
                    return map;
                }
                BlogWithUserNameDO last = blogDAO.findSortedRelyOnFavoriteById(sortFlag, marchText, list.get(i - 1));
                if(last==null) {
                    map.put("last", null);
                }else{
                    map.put("last", last.toModel());
                }
                BlogWithUserNameDO next = blogDAO.findSortedRelyOnFavoriteById(sortFlag, marchText, list.get(i + 1));
                if(next==null) {
                    map.put("next", null);
                }else{
                    map.put("next", next.toModel());
                }
                return map;
            }
        }
        map.put("last", null);
        map.put("next", null);
        return map;
    }

    @Override
    public Map<String, BlogWithUserName> findSortedRelyOnLike(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId) {
        List<String> list = blogDAO.findSortedIdsRelyOnLike(sortFlag, marchText,myBlogUserId,favoriteUserId);
        if(list==null) {
            return null;
        }
        Map<String, BlogWithUserName> map = new HashMap<>();
        if(list.size()<=1) {
            map.put("last", null);
            map.put("next", null);
            return map;
        }
        for(int i=0; i<list.size(); i++) {
            String id = list.get(i);
            if(id.equals(blogId)) {
                if(i==0){
                    map.put("last", null);
                    BlogWithUserNameDO next = blogDAO.findSortedRelyOnLikeById(sortFlag, marchText, list.get(i+1));
                    if(next==null) {
                        map.put("next", null);
                    }else{
                        map.put("next", next.toModel());
                    }
                    return map;
                }
                if(i==list.size()-1){
                    map.put("next", null);
                    BlogWithUserNameDO last = blogDAO.findSortedRelyOnLikeById(sortFlag, marchText, list.get(i-1));
                    if(last==null) {
                        map.put("last", null);
                    }else{
                        map.put("last", last.toModel());
                    }
                    return map;
                }
                BlogWithUserNameDO last = blogDAO.findSortedRelyOnLikeById(sortFlag, marchText, list.get(i - 1));
                if(last==null) {
                    map.put("last", null);
                }else{
                    map.put("last", last.toModel());
                }
                BlogWithUserNameDO next = blogDAO.findSortedRelyOnLikeById(sortFlag, marchText, list.get(i + 1));
                if(next==null) {
                    map.put("next", null);
                }else{
                    map.put("next", next.toModel());
                }
                return map;
            }
        }
        map.put("last", null);
        map.put("next", null);
        return map;
    }

    @Override
    public Map<String, BlogWithUserName> findSortedRelyOnComment(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId) {
        List<String> list = blogDAO.findSortedIdsRelyOnComment(sortFlag, marchText,myBlogUserId,favoriteUserId);
        if(list==null) {
            return null;
        }
        Map<String, BlogWithUserName> map = new HashMap<>();
        if(list.size()<=1) {
            map.put("last", null);
            map.put("next", null);
            return map;
        }
        for(int i=0; i<list.size(); i++) {
            String id = list.get(i);
            if(id.equals(blogId)) {
                if(i==0){
                    map.put("last", null);
                    BlogWithUserNameDO next = blogDAO.findSortedRelyOnCommentById(sortFlag, marchText, list.get(i+1));
                    if(next==null) {
                        map.put("next", null);
                    }else{
                        map.put("next", next.toModel());
                    }
                    return map;
                }
                if(i==list.size()-1){
                    map.put("next", null);
                    BlogWithUserNameDO last = blogDAO.findSortedRelyOnCommentById(sortFlag, marchText, list.get(i-1));
                    if(last==null) {
                        map.put("last", null);
                    }else{
                        map.put("last", last.toModel());
                    }
                    return map;
                }
                BlogWithUserNameDO last = blogDAO.findSortedRelyOnCommentById(sortFlag, marchText, list.get(i - 1));
                if(last==null) {
                    map.put("last", null);
                }else{
                    map.put("last", last.toModel());
                }
                BlogWithUserNameDO next = blogDAO.findSortedRelyOnCommentById(sortFlag, marchText, list.get(i + 1));
                if(next==null) {
                    map.put("next", null);
                }else{
                    map.put("next", next.toModel());
                }
                return map;
            }
        }
        map.put("last", null);
        map.put("next", null);
        return map;
    }

    @Override
    public Map<String, BlogWithUserName> findSortedRelyOnDate(String sortFlag, String marchText, String blogId,String myBlogUserId,String favoriteUserId) {
        List<String> list = blogDAO.findSortedIdsRelyOnDate(sortFlag, marchText,myBlogUserId,favoriteUserId);
        if(list==null) {
            return null;
        }
        Map<String, BlogWithUserName> map = new HashMap<>();
        if(list.size()<=1) {
            map.put("last", null);
            map.put("next", null);
            return map;
        }
        for(int i=0; i<list.size(); i++) {
            String id = list.get(i);
            if(id.equals(blogId)) {
                if(i==0){
                    map.put("last", null);
                    BlogWithUserNameDO next = blogDAO.findSortedRelyOnDateById(sortFlag, marchText, list.get(i+1));
                    if(next==null) {
                        map.put("next", null);
                    }else{
                        map.put("next", next.toModel());
                    }
                    return map;
                }
                if(i==list.size()-1){
                    map.put("next", null);
                    BlogWithUserNameDO last = blogDAO.findSortedRelyOnDateById(sortFlag, marchText, list.get(i-1));
                    if(last==null) {
                        map.put("last", null);
                    }else{
                        map.put("last", last.toModel());
                    }
                    return map;
                }
                BlogWithUserNameDO last = blogDAO.findSortedRelyOnDateById(sortFlag, marchText, list.get(i - 1));
                if(last==null) {
                    map.put("last", null);
                }else{
                    map.put("last", last.toModel());
                }
                BlogWithUserNameDO next = blogDAO.findSortedRelyOnDateById(sortFlag, marchText, list.get(i + 1));
                if(next==null) {
                    map.put("next", null);
                }else{
                    map.put("next", next.toModel());
                }
                return map;
            }
        }
        map.put("last", null);
        map.put("next", null);
        return map;
    }
}
