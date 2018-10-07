package uci.cs297p.proj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uci.cs297p.proj.model.Article;

import java.util.ArrayList;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String helloWorld(Model model) {
        ArrayList<Article> articles= new ArrayList<>();
        articles.add(new Article("Google", "google", "www.google.com"));
        articles.add(new Article("Facebook", "facebook", "www.facebook.com"));
        articles.add(new Article("Linkedin", "linkedin", "www.linkedin.com"));
        model.addAttribute("articles", articles);
        return "hello";
    }
}
