package com.lmh.blog_project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.entity.BlogsImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogsImagesService extends IService<BlogsImages> {
    Result<BlogsImages> uploadBlogsImages(MultipartFile file,String authorId, String blogId,String id);
    Boolean deleteBlogsOneImage(String fileName,String authorId);
    Boolean deleteBlogsSeveralImage(List<String> fileNames,String authorId);
    Boolean updateBlogsSeveralImage(List<String> fileNames,String authorId);
    Boolean deleteBlog(String blogId);

    void deleteRedundantBlogsImages(String authorId);
}
