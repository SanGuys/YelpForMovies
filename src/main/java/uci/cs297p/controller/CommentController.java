package uci.cs297p.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import uci.cs297p.common.Cnst;
import uci.cs297p.common.ServerResponse;
import uci.cs297p.model.Comment;
import uci.cs297p.model.User;
import uci.cs297p.service.ICommentService;
import uci.cs297p.service.IUserService;

@Controller
@RequestMapping("/comment/")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @RequestMapping("add.do")
//    @ResponseBody
    public RedirectView add(HttpSession session, Integer movieId, String content) {
        User user = (User) session.getAttribute(Cnst.CURRENT_USER);
        if (user != null) {
            ServerResponse serverResponse = commentService.add(user.getId(), movieId, content);
            if(serverResponse.isSucc()) {
                return new RedirectView("/movieDetailPage?ID=" + movieId);
            }
        }
        return new RedirectView("/");
//        return ServerResponse.succWithMsg("user not login!");
    }

    @RequestMapping("delete.do")
//    @ResponseBody
    public RedirectView delete(HttpSession session, @RequestParam("ID") Integer commentId, @RequestParam("movieId") Integer movieId) {
        User user = (User) session.getAttribute(Cnst.CURRENT_USER);
        if (user != null) {
            ServerResponse serverResponse = commentService.delete(user.getId(), commentId);
            if (serverResponse.isSucc()) {
                return new RedirectView("/movieDetailPage?ID=" + movieId);
            }
        }
        return new RedirectView("/");
    }

    @RequestMapping("edit.do")
    @ResponseBody
    public ServerResponse edit(HttpSession session, Integer commentId, String newContent) {
        User user = (User) session.getAttribute(Cnst.CURRENT_USER);
        if (user == null) {
            return ServerResponse.succWithMsg("user not login!");
        }
        return commentService.edit(user.getId(), commentId, newContent);
    }

    @RequestMapping("get.do")
    @ResponseBody
    public ServerResponse<Comment> getByCommentId(Integer commentId){
        return commentService.getByCommentId(commentId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<List<Comment>> listByUserIdMovieId(Integer userId, Integer movieId){
        return commentService.listByUserIdMovieId(userId, movieId);
    }
}
