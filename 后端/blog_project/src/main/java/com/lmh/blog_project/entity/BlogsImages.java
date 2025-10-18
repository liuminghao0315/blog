package com.lmh.blog_project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lmh.blog_project.enums.BlogsImagesStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("blogs_images")
public class BlogsImages implements Serializable {
    private static final long serialVersionUID = 504715691557342866L;

    private String id;
    private String authorId;
    private String blogId;
    private String fileName;
    private BlogsImagesStatus status;
    @TableField(fill = FieldFill.INSERT) // 插入时自动填充
    private LocalDateTime gmtCreated;
    @TableField(fill = FieldFill.INSERT_UPDATE) // 插入和更新时自动填充
    private LocalDateTime gmtModified;
}
