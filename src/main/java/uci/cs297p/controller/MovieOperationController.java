package uci.cs297p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uci.cs297p.model.Movie;
import uci.cs297p.model.MovieRecordForm;
import uci.cs297p.service.MovieOperationService;

import java.util.List;

@Controller
public class MovieOperationController {
    @Autowired
    private MovieOperationService movieOperationService;

    @RequestMapping("/")
    public String homepage(Model model){
        List<Movie> movieList = movieOperationService.searchMovie("");
        model.addAttribute("movieList", movieList);
        return "homepage";
    }

    @RequestMapping("/searchMovie")
    public String searchMovie(String keyWords, Model model){
        List<Movie> movieList = movieOperationService.searchMovie(keyWords);
        model.addAttribute("movieList", movieList);
        return "searchResult";
    }

    @RequestMapping("/addMovie")
    public String addMovie() {
        return "addMovie";
    }

    @RequestMapping("/addMovieSubmit")
    @ResponseBody
    public String addMovieSubmit(MovieRecordForm movieRecordForm) {
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
        return "movieDetailPage";
    }

    @RequestMapping("/editMovie")
    public String editMovie(@RequestParam("ID") Integer id, Model model){
        Movie movie = movieOperationService.getMovie(id);
        model.addAttribute("movie", movie);
        return "editMovie";
    }

    @RequestMapping("/editMovieSubmit")
    @ResponseBody
    public String editMovieSubmit(MovieRecordForm movieRecordForm) {
        movieOperationService.addMovie(movieRecordForm);
        return "Successfully Edited! \n" + movieRecordForm.toString();
    }

}
