package com.lmh.blog_project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmh.blog_project.entity.BlogsImages;
import com.lmh.blog_project.mapper.BlogsImagesMapper;
import com.lmh.blog_project.service.BlogsImagesService;
import org.springframework.stereotype.Service;

@Service
public class BlogsImagesServiceImpl extends ServiceImpl<BlogsImagesMapper, BlogsImages> implements BlogsImagesService {
}
