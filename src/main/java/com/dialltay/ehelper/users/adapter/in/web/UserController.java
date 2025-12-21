package com.dialltay.ehelper.users.adapter.in.web;

import com.dialltay.ehelper.users.port.in.CreateUserCommand;
import com.dialltay.ehelper.users.port.in.CreateUserUseCase;
import com.dialltay.ehelper.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @GetMapping
    public String users() {
        return "users/index";
    }

    @GetMapping("/form/create")
    public String createUserForm(Model model) {
        model.addAttribute("userForm", CreateUserCommand.defaultCommand());
        return "users/createForm";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("userForm") @Valid CreateUserCommand command,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "users/createForm";
        }
        var id = createUserUseCase.createUser(command);

        model.addAttribute(WebUtils.MSG_SUCCESS, "Utilisateur créé avec succès avec l'ID: " + id);
        return "redirect:/users";
    }
}
