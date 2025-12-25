package com.dialltay.ehelper.adapter.in.web;

import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.in.CreateUserUseCase;
import com.dialltay.ehelper.application.port.in.GetUserSliceUseCase;
import com.dialltay.ehelper.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserSliceUseCase userSliceUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserSliceUseCase userSliceUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.userSliceUseCase = userSliceUseCase;
    }

    @GetMapping
    public String users(Model model) {
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
