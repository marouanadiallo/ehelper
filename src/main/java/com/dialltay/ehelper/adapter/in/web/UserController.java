package com.dialltay.ehelper.adapter.in.web;

import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.in.CreateUserUseCase;
import com.dialltay.ehelper.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @GetMapping
    public String users(@RequestParam(value = "page", defaultValue = "0") Pageable pageable,
                        Model model) {
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

        model.addAttribute("message", WebUtils.getMessage(WebUtils.MSG_SUCCESS));
        return "redirect:/users";
    }
}
