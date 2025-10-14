package com.lmh.blog_project.api;


import com.lmh.blog_project.dto.CommentWithUserNameAndAvatarUrlDTO;
import com.lmh.blog_project.model.Comment;
import com.lmh.blog_project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class CommentApi {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/db/commnet/add")
    public Integer addComment(@RequestBody Comment comment) {
        return commentService.add(comment);
    }

    @RequestMapping("/findAllComments")
    public List<Comment> getComments() {
        return commentService.findAll();
    }

    @RequestMapping("/findCommentByArticleId")
    public List<Comment> getCommentsByArticleId(String articleId) {
        return commentService.findByArticleId(articleId);
    }

    @RequestMapping("/findCommentById")
    public Comment getCommentsById(String id) {
        return commentService.findById(id);
    }

    @RequestMapping("/findStructuredCommentByArticleId")
    public List<Comment> getStructuredCommentsByArticleId(String articleId) {
        return commentService.getStructuredComments(commentService.findByArticleId(articleId));
    }

    @RequestMapping("/findStructuredCommentWithUserNameAndAvatarUrl")
    public List<CommentWithUserNameAndAvatarUrlDTO> getCommentsWithUserNameAndAvatarUrl(String articleId, String userId) {
        return commentService.getStructuredCommentsWithUserNameAndAvatarUrl(commentService.findWithUserNameAndAvatarUrlDOByArticleId(articleId, userId))
                .stream().sorted(Comparator.comparing(CommentWithUserNameAndAvatarUrlDTO::getGmtCreated)).collect(Collectors.toList());
    }

    @RequestMapping("/db/comment/deleteByRootId")
    public Integer deleteByIds(String id) {
        return commentService.deleteByRootId(id);
    }

    @RequestMapping("/db/comment/deleteByParentId")
    public Integer deleteByParentIds(String id) {
        return commentService.deleteByParentId(id);
    }

    @RequestMapping("/db/comment/deleteByArticleId")
    public Integer deleteByArticleIds(String id) {
        return commentService.deleteByArticleId(id);
    }
}
