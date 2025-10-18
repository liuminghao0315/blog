package com.lmh.blog_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmh.blog_project.entity.BlogsImages;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BlogsImagesMapper extends BaseMapper<BlogsImages> {
}
