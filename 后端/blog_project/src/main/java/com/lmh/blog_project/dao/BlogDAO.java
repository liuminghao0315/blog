package com.lmh.blog_project.dao;

import com.lmh.blog_project.dataobject.BlogDO;
import com.lmh.blog_project.dataobject.BlogWithUserNameDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogDAO {
    // 增
    Integer add(BlogDO blogDO);

    // 删
    Integer delete(@Param("id") String id);

    // 查——全部
    List<BlogDO> findAll();
    // 查——单个
    BlogDO findById(@Param("id") String id);
    // 查——若干个
    List<BlogDO> findByIds(@Param("ids") List<String> ids);
    // 查——若干个（authorId，查某个人写的所有文章）
    List<BlogDO> findByAuthorId(@Param("authorId") String authorId);

    // 改
    Integer update(BlogDO blogDO);

    // 以下是根据排序/筛选进行查，并且是在数据库中联立查询，而不是在java中进行嵌套循环拼接，up代表升序，down代表降序
    // 发布时间
    List<BlogWithUserNameDO> findSortByDate(@Param("sortFlag")String sortFlag,@Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);
    // 点赞量
    List<BlogWithUserNameDO> findSortByLike(@Param("sortFlag")String sortFlag,@Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);
    // 收藏量
    List<BlogWithUserNameDO> findSortByFavorite(@Param("sortFlag")String sortFlag,@Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);
    // 评论数
    List<BlogWithUserNameDO> findSortByComment(@Param("sortFlag")String sortFlag,@Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);


    // 当前端Detail点击 上一篇文章 或 下一篇文章 的时候，返回的文章对象
    // 收藏量
    List<String> findSortedIdsRelyOnFavorite(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);
    BlogWithUserNameDO findSortedRelyOnFavoriteById(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("blogId")String blogId);

    //点赞量
    List<String> findSortedIdsRelyOnLike(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);
    BlogWithUserNameDO findSortedRelyOnLikeById(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("blogId")String blogId);

    //评论数
    List<String> findSortedIdsRelyOnComment(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);
    BlogWithUserNameDO findSortedRelyOnCommentById(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("blogId")String blogId);

    //发布时间
    List<String> findSortedIdsRelyOnDate(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("myBlogUserId")String myBlogUserId,@Param("favoriteUserId")String favoriteUserId);
    BlogWithUserNameDO findSortedRelyOnDateById(@Param("sortFlag")String sortFlag, @Param("marchText")String marchText,@Param("blogId")String blogId);
}
