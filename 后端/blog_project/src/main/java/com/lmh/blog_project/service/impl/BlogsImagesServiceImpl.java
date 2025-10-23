package com.lmh.blog_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.entity.BlogsImages;
import com.lmh.blog_project.enums.BlogsImagesStatus;
import com.lmh.blog_project.mapper.BlogsImagesMapper;
import com.lmh.blog_project.service.BlogsImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlogsImagesServiceImpl extends ServiceImpl<BlogsImagesMapper, BlogsImages> implements BlogsImagesService {
    @Autowired
    private RedisTemplate redisTemplate;

    //    private static final String BASE_PATH = "D:\\360MoveData\\Users\\23793\\Desktop\\博客网站\\七代版本-加入分页系统\\后端\\blog_project\\src\\main\\resources\\static\\images\\";
    private static final String BASE_PATH = "/root/project/images/";

    @Override
    public Result<BlogsImages> uploadBlogsImages(MultipartFile file, String authorId, String blogId, String id) {
        Result<BlogsImages> result = new Result<>();
        String randomStr = String.join("", UUID.randomUUID().toString().split("-"));
        String[] splitArr;
        try{
            splitArr = file.getOriginalFilename().split("\\.");
        }catch (Exception e){
            result.setSuccess(false);
            result.setCode("000");
            result.setMessage("无后缀名");
            return result;
        }
        String endStr = splitArr[splitArr.length - 1];
        String fileName = randomStr + "." +endStr;
        String filePath = BASE_PATH + fileName;
        InputStream in;
        OutputStream out;
        try {
            in = file.getInputStream();
            out = new FileOutputStream(filePath);
            byte[] bytes = new byte[1024];
            int byteNum;
            while((byteNum=in.read(bytes))!=-1){
                out.write(bytes,0,byteNum);
            }
            result.setSuccess(true);
            BlogsImages blogsImages = new BlogsImages();
            blogsImages.setAuthorId(authorId);
            blogsImages.setBlogId(blogId);
            blogsImages.setId(id);
            blogsImages.setStatus(BlogsImagesStatus.SIDEBAR_ONLY);
            blogsImages.setFileName(fileName);

            save(blogsImages);

            if(redisTemplate.opsForHash().get("blogImages",authorId)==null){
                redisTemplate.opsForHash().put("blogImages",authorId,new ArrayList<String>());
            }
            List<String> oldImgs = (List<String>) redisTemplate.opsForHash().get("blogImages",authorId);
            oldImgs.add(fileName);
            redisTemplate.opsForHash().put("blogImages",authorId,oldImgs);

            result.setData(blogsImages);

            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Boolean deleteBlogsOneImage(String fileName,String authorId) {
        QueryWrapper<BlogsImages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);
        boolean removeDB = remove(queryWrapper);
        boolean removeFile = new File(BASE_PATH + fileName).delete();
        List<String> oldImgs = (List<String>) redisTemplate.opsForHash().get("blogImages",authorId);
        boolean removeRedis = oldImgs.remove(fileName);
        redisTemplate.opsForHash().put("blogImages",authorId,oldImgs);
        return removeDB && removeFile&&removeRedis;
    }

    public Boolean deleteBlogsOneImage(String fileName) {
        QueryWrapper<BlogsImages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);
        boolean removeDB = remove(queryWrapper);
        boolean removeFile = new File(BASE_PATH + fileName).delete();
        return removeDB && removeFile;
    }

    @Override
    public Boolean deleteBlogsSeveralImage(List<String> fileNames,String authorId) {
        boolean result = true;
        for (String fileName : fileNames) {
            Boolean b = deleteBlogsOneImage(fileName,authorId);
            if(!b){
                result = false;
            }
        }
        return result;
    }

    public Boolean deleteBlogsSeveralImage(List<String> fileNames) {
        boolean result = true;
        for (String fileName : fileNames) {
            Boolean b = deleteBlogsOneImage(fileName);
            if(!b){
                result = false;
            }
        }
        return result;
    }

    @Override
    public Boolean updateBlogsSeveralImage(List<String> fileNames,String authorId) {
        boolean result = true;
        for (String fileName : fileNames) {
            UpdateWrapper<BlogsImages> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("file_name", fileName);
            updateWrapper.set("status", BlogsImagesStatus.ARTICLE_ONLY);
            boolean u = update(updateWrapper);
            List<String> oldImgs = (List<String>) redisTemplate.opsForHash().get("blogImages",authorId);
            boolean removeRedis = oldImgs.remove(fileName);
            redisTemplate.opsForHash().put("blogImages",authorId,oldImgs);
            if(!u||!removeRedis){
                result = false;
            }
        }
        return result;
    }

    @Override
    public Boolean deleteBlog(String blogId) {
        QueryWrapper<BlogsImages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        List<String> fileNames = getBaseMapper().selectList(queryWrapper)
                .stream().map(BlogsImages::getFileName).collect(Collectors.toList());
        return deleteBlogsSeveralImage(fileNames);
    }

    @Override
    public void deleteRedundantBlogsImages(String authorId) {
        Object blogImages = redisTemplate.opsForHash().get("blogImages", authorId);
        if(blogImages==null){
            return;
        }
//        System.out.println(blogImages);
        List<String> list = (List<String>) blogImages;
        deleteBlogsSeveralImage(list,authorId);
        redisTemplate.opsForHash().delete("blogImages",authorId);
    }
}
