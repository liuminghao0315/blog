package com.lmh.blog_project.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lmh.blog_project.common.Result;
import com.lmh.blog_project.entity.BlogsImages;
import com.lmh.blog_project.enums.BlogsImagesStatus;
import com.lmh.blog_project.service.BlogsImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class BlogsImagesApi {
    @Autowired
    private BlogsImagesService blogsImagesService;

    private static final String BASE_PATH = "D:\\360MoveData\\Users\\23793\\Desktop\\博客网站\\七代版本-加入分页系统\\后端\\blog_project\\src\\main\\resources\\images\\";

    @RequestMapping("/upload/blogsImages")
    public Result<BlogsImages> uploadBlogsImages(@RequestBody MultipartFile file, @RequestParam("authorId")String authorId, @RequestParam("blogId")String blogId, @RequestParam("id")String id) {
        Result<BlogsImages> result = new Result<>();
        String randomStr = String.join("",UUID.randomUUID().toString().split("-"));
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
        String filePath = BASE_PATH + randomStr + "." +endStr;
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
            blogsImages.setFileName(randomStr + "." +endStr);

            blogsImagesService.save(blogsImages);

            result.setData(blogsImages);

            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @RequestMapping("/deleteOne/blogsImages")
    public Boolean deleteBlogsOneImage(@RequestParam("fileName")String fileName) {
        QueryWrapper<BlogsImages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);
        boolean removeDB = blogsImagesService.remove(queryWrapper);
        boolean removeFile = new File(BASE_PATH + fileName).delete();
        return removeDB && removeFile;
    }

    @RequestMapping("/deleteSeveral/blogsImages")
    public Boolean deleteBlogsSeveralImage(@RequestParam("fileNames") List<String> fileNames) {
        boolean result = true;
        for (String fileName : fileNames) {
            Boolean b = deleteBlogsOneImage(fileName);
            if(!b){
                result = false;
            }
        }
        return result;
    }

    @RequestMapping("/updateSeveral/blogsImages")
    public Boolean updateBlogsSeveralImage(@RequestParam("fileNames") List<String> fileNames) {
        boolean result = true;
        for (String fileName : fileNames) {
            UpdateWrapper<BlogsImages> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("file_name", fileName);
            updateWrapper.set("status", BlogsImagesStatus.ARTICLE_ONLY);
            boolean u = blogsImagesService.update(updateWrapper);
            if(!u){
                result = false;
            }
        }
        return result;
    }

    @RequestMapping("/deleteBlog/blogsImages")
    public Boolean deleteBlog(@RequestParam("blogId")String blogId) {
        QueryWrapper<BlogsImages> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        List<String> fileNames = blogsImagesService.getBaseMapper().selectList(queryWrapper)
                .stream().map(BlogsImages::getFileName).collect(Collectors.toList());
        return deleteBlogsSeveralImage(fileNames);
    }
}
