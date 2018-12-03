package uci.cs297p.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import uci.cs297p.common.Cnst;
import uci.cs297p.common.ResponseCode;
import uci.cs297p.common.ServerResponse;
import uci.cs297p.model.Movie;
import uci.cs297p.model.UMRelation;
import uci.cs297p.model.User;
import uci.cs297p.model.UserProfileForm;
import uci.cs297p.service.IUserService;
import uci.cs297p.service.MovieOperationService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private MovieOperationService movieOperationService;

    @RequestMapping(value = "/signIn")
    public String signIn() {
        return "signIn";
    }

    @RequestMapping(value = "/signUp")
    public String signUp() {
        return "signUp";
    }

    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
    public String userProfile(@PathVariable("userId") Integer userId, Model model) {
        ServerResponse<List<UMRelation>> serverResponse = userService.getCollections(userId);
        List<UMRelation> umRelationList = new ArrayList<>();
        List<Movie> movieList = new ArrayList<>();
        if(serverResponse.isSucc()) umRelationList = serverResponse.getData();
        for(UMRelation umRelation : umRelationList) {
            Movie movie = movieOperationService.getMovie(umRelation.getMovieId());
            if(movie != null) movieList.add(movie);
        }
        model.addAttribute("movieList", movieList);
        return "profile";
    }

    @RequestMapping(value="/profile/update/{userId}", method = RequestMethod.GET)
    public String userProfileUpdate(HttpSession session, Model model){
        User user = (User) session.getAttribute(Cnst.CURRENT_USER);
        model.addAttribute("user", user);
        return "editProfile";
    }

    @RequestMapping(value="/profile/{userId}", method=RequestMethod.PUT)
    public RedirectView userProfileUpdateSubmit(@PathVariable("userId") Integer userId, HttpSession session, UserProfileForm userProfileForm) {
        User user = (User) session.getAttribute(Cnst.CURRENT_USER);
        if(user == null || user.getId() != userId) return null;
        ServerResponse serverResponse = userService.updateUserInfo(user, userProfileForm);
        if (serverResponse.isSucc()) session.setAttribute(Cnst.CURRENT_USER, serverResponse.getData());
        return new RedirectView("/user/profile/" + userId);
    }

    //Verified
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse serverResponse = userService.login(username, password);
        if (serverResponse.isSucc()) session.setAttribute(Cnst.CURRENT_USER, serverResponse.getData());
        return serverResponse;
    }

    //Verified
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public RedirectView logout(HttpSession session) {
        session.removeAttribute(Cnst.CURRENT_USER);
        return new RedirectView("/");

    }

    //verified
    @RequestMapping(value = "signup.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> signup(User user) {
        return userService.signup(user);
    }

    //Verified
    @RequestMapping(value = "forget_password_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetPasswordGetQuestion(String username) {
        return userService.getQuestion(username);
    }

    //Verified
    @RequestMapping(value = "forget_password_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetPasswordCheckAnswer(String username, String answer) {
        //return ServerResponse<null> for wrong, ...<token> for correct.
        //forgetToken is a certification for the agent who has answered the question correctly.
        return userService.checkAnswer(username, answer);
    }

    //Verified
    @RequestMapping(value = "forget_password_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetPasswordResetPassword(String username, String newPassword, String token) {
        return userService.resetPassword(username, newPassword, token);
    }

    //Verified
    //reset password while login. use HttpSession & oldPassword instead of Question-Answer-Token verification.
    @RequestMapping(value = "reset_password_login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPasswordLogin(HttpSession session, String oldPassword, String newPassword) {
        User user = (User) session.getAttribute(Cnst.CURRENT_USER);
        if (user == null) return ServerResponse.succWithMsg("user not login!");
        else return userService.resetPasswordLogin(user, oldPassword, newPassword);
    }

    //Verified
    @RequestMapping(value = "get_userinfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User loginUser = (User) session.getAttribute(Cnst.CURRENT_USER);
        if (loginUser == null)
            return ServerResponse.failWithCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "user not login!");
        else {
            User loginUserFull = userService.getUserInfo(loginUser).getData();
            loginUserFull.setPassword("");
            return ServerResponse.succWithMsgData("getUserInfo success", loginUserFull);
        }
    }

    @RequestMapping(value = "update_userinfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateUserInfo(HttpSession session, User user) {
        User loginUser = (User) session.getAttribute(Cnst.CURRENT_USER);
        if (loginUser == null) return ServerResponse.failWithMsg("user not login!");
        else {
            //user is user-typeIn. loginUser is userInfo in Session.
            //set id, name according to loginUser.
            user.setId(loginUser.getId());
            user.setUsername(loginUser.getUsername());
            ServerResponse<User> response = userService.updateUserInfo(user);
            if (response.isSucc()) {
                //Update userInfo success, update session
                session.setAttribute(Cnst.CURRENT_USER, response.getData());
                return ServerResponse.succWithMsg("updateUserInfo success");
            } else {
                return ServerResponse.failWithMsg("updateUserInfo failed");
            }
        }
    }


}
