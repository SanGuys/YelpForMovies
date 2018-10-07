package uci.cs297p.proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EditMovieRecordController {
    @RequestMapping("/editMovieRecord")
    public String editMovieRecord() {
        return "editMoviesRecord";
    }
}
