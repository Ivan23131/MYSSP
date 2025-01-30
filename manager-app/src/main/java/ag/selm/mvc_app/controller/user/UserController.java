package ag.selm.mvc_app.controller.user;


import ag.selm.mvc_app.entity.Authority;
import ag.selm.mvc_app.entity.SelmagUser;
import ag.selm.mvc_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("/start")
    public String getStartPage(@AuthenticationPrincipal User user) {
        // Получаем роль "client" из базы данных
        Authority clientAuthority = userService.findAuthority("client");

        // Проверяем, есть ли у пользователя роль "client"
        boolean isClient = user.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(clientAuthority.getAuthority()));

        if (isClient) {
            return "client/start";
        } else {
            return "manager/event/list";
        }
    }

    // Отображение формы добавления пользователя
    @GetMapping("/add-user")
    public String showAddUserForm() {
        return "user/add_user";
    }

    // Обработка данных формы
    @PostMapping("/add-user")
    public String addUser(
            @RequestParam String username,
            @RequestParam String passwordHash,
            @RequestParam String role,
            Model model) {

        // Проверяем, существует ли пользователь с таким именем или паролем
        if (userService.isUserExists(username, "{noop}" + passwordHash)) {
            model.addAttribute("error", "Пользователь с таким именем или паролем уже существует!");
            return "user/add_user";
        }

        if (!role.equalsIgnoreCase("клиент") && !role.equalsIgnoreCase("организатор")) {
            model.addAttribute("error", "Некорректная роль! Допустимые значения: клиент, организатор.");
            return "add-user";
        }

        // Создаем нового пользователя
        SelmagUser user = new SelmagUser();
        user.setUsername(username);
        user.setPasswordHash("{noop}" + passwordHash);

        // Находим или создаем роль
        Authority userAuthority;
        if (role.equals("клиент")) {
            userAuthority = userService.findAuthority("client");
        } else {
            userAuthority = userService.findAuthority("organizer");
        }

        // Добавляем роль пользователю
        user.getAuthorities().add(userAuthority);
        // Сохраняем пользователя в базу данных
        userService.saveUser(user);

        model.addAttribute("message", "Пользователь успешно добавлен!");
        return "user/login_page";
    }
}
