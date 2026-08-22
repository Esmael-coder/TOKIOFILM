package com.tokio.filme.controllers;

import com.tokio.filme.dtos.UpdateUserDTO;
import com.tokio.filme.entities.User;
import com.tokio.filme.security.AuthenticatedUser;
import com.tokio.filme.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticatedUser authenticatedUser;

    @GetMapping("/profile")
    public String showProfile(Model model){
        User user = authenticatedUser.getAuthenticatedUser();
        UpdateUserDTO userDTO = new UpdateUserDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthDate(user.getBirthDate());

        model.addAttribute("updateUserDTO", userDTO);
        model.addAttribute("profilePicture", user.getProfilePicture());

        return "profile";
    }

    @PostMapping("/profile/update")
    public String UpdateUserData(Model model, @Valid UpdateUserDTO updateUserDTO, BindingResult result, @RequestParam(value = "profilePicture") MultipartFile file, RedirectAttributes redirect) throws IOException {

        if (result.hasErrors()){
            User user = authenticatedUser.getAuthenticatedUser();
            model.addAttribute("profilePicture", user.getProfilePicture());
            return "profile";
        }

        User user = authenticatedUser.getAuthenticatedUser();
        try {
            userService.UpdateUser(user, updateUserDTO, file);

        } catch (IOException e) {
            log.error("Erro ao tentar atualizar foto de perfil: {}", e.getMessage());
            throw new RuntimeException("Não foi possível atualizar a foto, tente novamente", e);
        }

        redirect.addFlashAttribute(
                "success",
                "Perfil atualizado com sucesso."
        );

        return "redirect:/";
    }
}
