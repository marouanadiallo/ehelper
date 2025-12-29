package com.dialltay.ehelper.adapter.in.web;

import com.dialltay.ehelper.util.WebUtils;
import com.dialltay.ehelper.adapter.in.requestDTO.SliceCursor;
import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.in.CreateUserUseCase;
import com.dialltay.ehelper.application.port.in.GetUserSliceUseCase;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

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
    public String users(Model model) {
        return "users/index";
    }

    @GetMapping("/slice")
    @HxRequest
    public View htmxUsersSlice(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                               @RequestParam(name = "size", defaultValue = "5", required = false) int size,
                               Model model) {
        var userSlice = userSliceUseCase.userSliceAt(page, size);
        model.addAttribute("userSlice", userSlice);
        model.addAttribute("sliceCursor", SliceCursor.moveTo(page, size));
        return FragmentsRendering
                .fragment("users/fragments :: userTableRows")
                .fragment("users/fragments :: userTablePagination")
                .build();
    }

    @GetMapping("/{id}/details")
    @HxRequest
    public String userDetails(@PathVariable Long id, Model model) {
        // Implementation for user details goes here
        return "users/details";
    }

    @PostMapping("/upload/batch")
    @HxRequest
    public String createUserBatch(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", WebUtils.getMessage("file.upload.empty"));
            return "users/index";
        }
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
