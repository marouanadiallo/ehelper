package com.dialltay.ehelper.adapter.in.web;

import com.dialltay.ehelper.util.WebUtils;
import com.dialltay.ehelper.adapter.in.requestDTO.SliceCursor;
import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.in.CreateUserUseCase;
import com.dialltay.ehelper.application.port.in.GetUserSliceUseCase;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserSliceUseCase userSliceUseCase;
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserSliceUseCase userSliceUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.userSliceUseCase = userSliceUseCase;
    }

    @ModelAttribute("currentUri")
    public String contextUri() {
        return WebUtils.getRequest().getRequestURI();
    }

    @GetMapping
    public String users() {
        return "users/index";
    }

    @GetMapping("/slice")
    @HxRequest
    public String htmxUsersSlice(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                 @RequestParam(name = "size", defaultValue = "5", required = false) int size,
                                 Model model) {
        var userSlice = userSliceUseCase.userSliceAt(page, size);
        logger.info("user slice number : {}", userSlice.getNumber());
        model.addAttribute("userSlice", userSlice);
        model.addAttribute("sliceCursor", SliceCursor.moveTo(page + 1, size));
        return "users/fragments :: userTableRows";
    }

    @GetMapping("/{id}/details")
    public String userDetails(@PathVariable Long id, Model model) {
        // Implementation for user details goes here
        return "users/details";
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
