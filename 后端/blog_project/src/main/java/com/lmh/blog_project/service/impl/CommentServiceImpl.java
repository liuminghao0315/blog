package com.lmh.blog_project.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmh.blog_project.dao.CommentDAO;
import com.lmh.blog_project.dataobject.CommentDO;
import com.lmh.blog_project.dataobject.CommentWithUserNameAndAvatarUrlDO;
import com.lmh.blog_project.dto.CommentWithUserNameAndAvatarUrlDTO;
import com.lmh.blog_project.model.Comment;
import com.lmh.blog_project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

//    @Autowired
    private CommentDAO commentDAO;

    // 构造器注入，比Autowired更好
    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public Integer add(Comment comment) {
        return commentDAO.add(new CommentDO(comment));
    }

    @Override
    public Integer delete(String id) {
        return commentDAO.delete(id);
    }

    @Override
    public Integer deleteByRootId(String rootId) {
        return commentDAO.deleteByRootId(rootId);
    }

    @Override
    public Integer deleteByParentId(String parentId) {
        return commentDAO.deleteByParentId(parentId);
    }

    @Override
    public Integer deleteByArticleId(String articleId) {
        return commentDAO.deleteByArticleId(articleId);
    }

    @Override
    public Integer deleteByAuthorId(String authorId) {
        return commentDAO.deleteByAuthorId(authorId);
    }

    @Override
    public List<Comment> findAll() {
        return commentDAO.findAll().stream().map(CommentDO::toModel).collect(Collectors.toList());
    }

    @Override
    public Comment findById(String id) {
        CommentDO commentDO = commentDAO.findById(id);
        if(commentDO == null) return null;
        return commentDO.toModel();
    }

    @Override
    public List<Comment> findByArticleId(String articleId) {
        List<CommentDO> commentDOs = commentDAO.findByArticleId(articleId);
        if(commentDOs == null) return null;
        return commentDOs.stream().map(CommentDO::toModel).collect(Collectors.toList());
    }

    @Override
    public List<CommentWithUserNameAndAvatarUrlDTO> findWithUserNameAndAvatarUrlDOByArticleId(String articleId, String userId) {
        List<CommentWithUserNameAndAvatarUrlDO> result = commentDAO.findWithUserNameAndAvatarUrlDOByArticleId(articleId, userId);
        if(result == null) return null;
        return result.stream().map(CommentWithUserNameAndAvatarUrlDO::toDTO).collect(Collectors.toList());
    }

    @Override
    public Integer update(Comment comment) {
        return commentDAO.update(new CommentDO(comment));
    }

    @Override
    public List<Comment> getStructuredComments(List<Comment> originComments) {
        List<Comment> resultComments = new ArrayList<>();
        Map<String,Comment> commentMap = new HashMap<>();
        for (Comment originComment : originComments) {
            commentMap.put(originComment.getId(), originComment);
            if(originComment.getParentId() == null) {
                resultComments.add(originComment);
            }
        }
        for (Comment originComment : originComments) {
            if(originComment.getParentId() != null) {
                Comment parentComment = commentMap.get(originComment.getParentId());
                if(parentComment.getChildren()==null) {
                    parentComment.setChildren(new ArrayList<>());
                }
                parentComment.getChildren().add(originComment);
            }
        }
        return resultComments;
    }

    @Override
    public List<CommentWithUserNameAndAvatarUrlDTO> getStructuredCommentsWithUserNameAndAvatarUrl(List<CommentWithUserNameAndAvatarUrlDTO> originComments) {
        List<CommentWithUserNameAndAvatarUrlDTO> resultComments = new ArrayList<>();
        Map<String,CommentWithUserNameAndAvatarUrlDTO> commentMap = new HashMap<>();
        for (CommentWithUserNameAndAvatarUrlDTO originComment : originComments) {
            commentMap.put(originComment.getId(), originComment);
            if(originComment.getParentId() == null) {
                resultComments.add(originComment);
            }
        }
        for (CommentWithUserNameAndAvatarUrlDTO originComment : originComments) {
            if(originComment.getParentId() != null) {
                CommentWithUserNameAndAvatarUrlDTO parentComment = commentMap.get(originComment.getParentId());
                originComment.setParentName(parentComment.getUserName());
                if(parentComment.getParentId() == null) {
                    originComment.setSecond(true);
                }
                if(parentComment.getChildren()==null) {
                    parentComment.setChildren(new ArrayList<>());
                }
                parentComment.getChildren().add(originComment);
            }
        }
        return resultComments;
    }
}
