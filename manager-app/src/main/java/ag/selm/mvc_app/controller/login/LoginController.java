package ag.selm.mvc_app.controller.login;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage() {
        return "user/login_page";
    }

    @GetMapping("/login/start")
    public String getStartPage() {
        return "manager/events/list";
    }
}
