package uci.cs297p.service;

import com.mysql.cj.util.StringUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uci.cs297p.common.ServerResponse;
import uci.cs297p.model.Comment;
import uci.cs297p.model.CommentMapper;

@Service
public class CommentServiceImp implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public ServerResponse add(Integer userId, Integer movieId, String content) {
        if (userId == null) {
            return ServerResponse.failWithMsg("userId is empty!");
        }
        if (movieId == null) {
            return ServerResponse.failWithMsg("movieId is empty!");
        }
        if (StringUtils.isNullOrEmpty(content)) {
            return ServerResponse.failWithMsg("content is empty!");
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setMovieId(movieId);
        comment.setContent(content);
        int rowCnt = commentMapper.insertSelective(comment);
        if (rowCnt > 0) {
            return ServerResponse.succWithMsgData("addComment succeed.", comment);
        } else {
            return ServerResponse.failWithMsg("addComment failed.");
        }
    }

    @Override
    public ServerResponse delete(Integer userId, Integer commentId) {
        if (userId == null) {
            return ServerResponse.failWithMsg("userId is empty!");
        }
        if (commentId == null) {
            return ServerResponse.failWithMsg("commentId is empty!");
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment.getUserId() != userId) {
            return ServerResponse.failWithMsg("delete failed. author not match.");
        }
        int rowCnt = commentMapper.deleteByPrimaryKey(commentId);
        if (rowCnt > 0) {
            return ServerResponse.succWithMsg("deleteComment succeed.");
        } else {
            return ServerResponse.failWithMsg("deleteComment failed.");
        }
    }

    @Override
    public ServerResponse edit(Integer userId, Integer commentId, String newContent) {
        if (userId == null) {
            return ServerResponse.failWithMsg("userId is empty!");
        }
        if (commentId == null) {
            return ServerResponse.failWithMsg("commentId is empty!");
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment.getUserId() != userId) {
            return ServerResponse.failWithMsg("editComment failed. author not match.");
        }
        comment.setContent(newContent);
        int rowCnt = commentMapper.updateByPrimaryKeySelective(comment);
        if (rowCnt > 0) {
            return ServerResponse.succWithMsg("editComment succeed.");
        } else {
            return ServerResponse.failWithMsg("editComment failed.");
        }
    }

    @Override
    public ServerResponse<Comment> getByCommentId(Integer commentId) {
        if (commentId == null) {
            return ServerResponse.failWithMsg("commentId is empty!");
        }
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment == null) {
            return ServerResponse.failWithMsg("comment not exists!");
        } else {
            return ServerResponse.succWithMsgData("getComment succeed.", comment);
        }
    }

    @Override
    public ServerResponse<List<Comment>> listByUserIdMovieId(Integer userId, Integer movieId) {
        if (userId == null && movieId == null) {
            return ServerResponse.failWithMsg("Both userId and movieId are empty!");
        }
        List<Comment> commentList = commentMapper.selectByUserIdMovieId(userId, movieId);
        return ServerResponse.succWithMsgData("listByUserIdMovieId succeed.", commentList);
    }
}
