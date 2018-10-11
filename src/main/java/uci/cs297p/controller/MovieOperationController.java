package uci.cs297p.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uci.cs297p.model.Movie;
import uci.cs297p.model.MovieRecordForm;
import uci.cs297p.service.MovieOperationService;

import java.util.List;

@Controller
public class EditMovieRecordController {
    @Autowired
    private MovieOperationService movieOperationService;

    @RequestMapping("/")
    public String homepage(){
        return "homepage";
    }

    @RequestMapping("/searchMovie")
    public String searchMovie(String keyWords){
        List<Movie> movieList = movieOperationService.searchMovie(keyWords);
    }

    @RequestMapping("/addMovie")
    public String addMovie() {
        return "addMovie";
    }

    @RequestMapping("/addMovieSubmit")
    @ResponseBody
    public String addMovieSubmit(MovieRecordForm movieRecordForm) {
        movieOperationService.addMovie(movieRecordForm);
        return "Success! \n" + movieRecordForm.toString();
    }

}
