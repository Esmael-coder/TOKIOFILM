package com.tokio.filme.controllers;

import com.tokio.filme.dtos.UserRegisterDTO;
import com.tokio.filme.enuns.RoleValue;
import com.tokio.filme.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    List<RoleValue> roles = List.of(RoleValue.values());

    @GetMapping("/signup")
    public String userRegistrationGet(Model model){

        model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        model.addAttribute("roles", roles);
        return "signup";
    }

    @PostMapping("/signup")
    public String userRegistrationPost(@Valid UserRegisterDTO userRegisterDTO, BindingResult result,Model model, @RequestParam("profilePicture") MultipartFile file) throws IOException {
        if (result.hasErrors()){
            model.addAttribute("roles", roles);
            return "signup";
        }

        String message = userService.saveUser(userRegisterDTO, file);
        return "redirect:/login";
    }
}
