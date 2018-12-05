package uci.cs297p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uci.cs297p.common.ServerResponse;
import uci.cs297p.model.*;
import uci.cs297p.service.IUserService;
import uci.cs297p.service.MovieOperationService;
import uci.cs297p.service.UserServiceImpl;

import java.util.*;

@Controller
public class MovieOperationController {
    @Autowired
    private MovieOperationService movieOperationService;

    @Autowired
    private CommentController commentController;

    @Autowired
    private IUserService userService;

    private final static int DISPLAYLISTSIZE = 9;

    @RequestMapping("/")
    public String homepage(Model model){
        List<Movie> movieList = movieOperationService.getMovieListByRandom(DISPLAYLISTSIZE);
        model.addAttribute("movieList", movieList);
        return "homepage";
    }

    @RequestMapping("/searchMovie")
    public String searchMovie(String keyWords, Model model){
        List<Movie> movieList = movieOperationService.searchMovie(keyWords);
        model.addAttribute("movieList", movieList);
        return "searchResult";
    }

    @RequestMapping(value="/addMovie", method = RequestMethod.POST)
    public String addMovie() {
        return "addMovie";
    }

    @RequestMapping(value="/addMovieSubmit", method=RequestMethod.POST)
    @ResponseBody
    public String addMovieSubmit(MovieRecordForm movieRecordForm, BindingResult bindingResult) {
        movieOperationService.addMovie(movieRecordForm);
        return "Successfully Added! \n" + movieRecordForm.toString();
    }

    @RequestMapping("/deleteMovie")
    @ResponseBody
    public String deleteMovie(Integer ID){
        movieOperationService.deleteMovie(ID);
        return "Successfully Deleted! \n" + ID;
    }

    @RequestMapping("/movieDetailPage")
    public String movieDetailPage(@RequestParam("ID") Integer id, Model model){
        Movie movie = movieOperationService.getMovie(id);
        model.addAttribute("movie", movie);
        List<Comment> commentList = commentController.listByUserIdMovieId(null, id).getData();

        // get corresponding userNames and userIds of these comments
        List<String> userNames = new ArrayList<>();
        List<Integer> userIds = new ArrayList<>();
        for(Comment cmt : commentList) {
            String username = "null";
            if(cmt.getUserId() != null) {
                Integer userId = cmt.getUserId();
                if(userId != null) {
                    username = userService.getUserName(userId);
                }
            }
            userNames.add(username);
            userIds.add(cmt.getUserId());
        }

        model.addAttribute("userNamesOfCommentList", userNames);
        model.addAttribute("userIdsOfCommentList", userIds);
        model.addAttribute("commentList", commentList);
        return "movieDetailPage";

    }

    @RequestMapping(value="/editMovie", method = RequestMethod.GET)
    public String editMovie(@RequestParam("ID") Integer id, Model model){
        Movie movie = movieOperationService.getMovie(id);
        model.addAttribute("movie", movie);
        return "editMovie";
    }

    @RequestMapping(value="/editMovieSubmit", method=RequestMethod.POST)
    @ResponseBody
    public String editMovieSubmit(MovieRecordForm movieRecordForm, BindingResult bindingResult) {
        movieOperationService.editMovie(movieRecordForm);
        return "Successfully Edited! \n" + movieRecordForm.toString();
    }

    @RequestMapping(value="/getCollection.do", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCollection(Integer userId, Integer movieId, Model model) {
        Map<String, Object> map = new HashMap<>();
        ServerResponse<UMRelation> serverResponse = userService.getCollection(new UMRelationKey(movieId, userId));
        if(serverResponse.isSucc()) {
//            model.addAttribute("umRelation", (UMRelation) serverResponse.getData());
            map.put("umRelation", (UMRelation) serverResponse.getData());
        } else
            map.put("umRelation", null);
        return map;
    }

    @RequestMapping(value="/updateCollection.do", method=RequestMethod.POST)
    @ResponseBody
    public String updateCollection(String userId, String movieId) {
        ServerResponse<UMRelation> serverResponse = userService.updateCollection(new UMRelationKey(Integer.valueOf(movieId), Integer.valueOf(userId)));
        if(serverResponse.isSucc()) return "succeed! \n" + serverResponse.getData();
        return "failed";
    }

    @RequestMapping(value="/updateRating.do", method=RequestMethod.POST)
    @ResponseBody
    public String updateRating(String userId, String movieId, String score) {
        ServerResponse<UMRelation> serverResponse = userService.updateRating(new UMRelationKey(Integer.valueOf(movieId), Integer.valueOf(userId)), Integer.valueOf(score));
        if(serverResponse.isSucc()) return "succeed! \n" + serverResponse.getData();
        return "failed";
    }
}
