package uci.cs297p.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class SignInController {
    @RequestMapping(value = "/signIn")
    public String signIn() {
        return "signIn";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String signInCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        JSONObject json = new JSONObject();
        if (username.equals("admin") && password.equals("admin")) {//这里没有用数据库验证
            json.put("result", "success");
        } else {
            json.put("result", "error");
        }
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
}
