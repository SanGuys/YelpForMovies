package uci.cs297p.service;

import java.util.List;
import uci.cs297p.common.ServerResponse;
import uci.cs297p.model.Comment;

public interface ICommentService {

    ServerResponse<Comment> add(Integer userId, Integer movieId, String content);

    ServerResponse delete(Integer userId, Integer commentId);

    ServerResponse edit(Integer userId, Integer commentId, String newContent);

    ServerResponse<Comment> getByCommentId(Integer commentId);

    ServerResponse<List<Comment>> listByUserIdMovieId(Integer userId, Integer movieId);
}
