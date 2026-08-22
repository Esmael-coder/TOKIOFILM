package com.tokio.filme.services;

import com.tokio.filme.dtos.UpdateUserDTO;
import com.tokio.filme.dtos.UserRegisterDTO;
import com.tokio.filme.entities.Role;
import com.tokio.filme.entities.User;
import com.tokio.filme.enuns.RoleValue;
import com.tokio.filme.exceptions.SaveException;
import com.tokio.filme.exceptions.UsernameOrEmailExistException;
import com.tokio.filme.repositories.RoleRepository;
import com.tokio.filme.repositories.UserRepository;
import com.tokio.filme.security.AuthenticatedUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticatedUser authenticatedUser;

    @Transactional // ou todas as consultas são concluídas com sucesso, ou nenhuma e aplicada(se findByAuthority falhar, o save ‘user’ não será executado.)
    public String saveUser(UserRegisterDTO userRegisterDTO, MultipartFile file) throws IOException {

        log.info("Tentando salvar usuário: {}", userRegisterDTO.getUsername());

        if (thisEmailExists(userRegisterDTO.getEmail().toLowerCase()) || thisUsernameExists(userRegisterDTO.getUsername().toLowerCase())){
            log.warn("Já exite um usuário com esse email ou username");
            throw new UsernameOrEmailExistException("Já existe um user com esse email ou username");
        }
        //salvar imagem de perfil
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/users");

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );

        Role role;

        /*
        * Para atribuir a role admin a um user, verifico se é um utilizador que está fazendo o pedido está autenticado e se esse utilizador é admin
        * Se for um user, a role vem null e se for um admin e não selecionou a role, a vole vem vem string vazia (blank)
        * */

        if (isAdmin() && userRegisterDTO.getRole() != null && !userRegisterDTO.getRole().isEmpty()){

            role = roleRepository.findByAuthority(RoleValue.valueOf(userRegisterDTO.getRole()))
                    .orElseThrow(()-> {
                        log.warn("Nao foi possível carregar a role: {}", userRegisterDTO.getRole());
                        return new RuntimeException("Erro ao carregar Role");
                    });
        } else {
            role = roleRepository.findByAuthority(RoleValue.USER)
                    .orElseThrow(()->{
                        log.warn("Não foi possível carregar a role {}", RoleValue.USER);
                        return new RuntimeException("Erro ao carregar a role");
                    });
        }

        User user = new User(userRegisterDTO);
        user.getRoles().add(role);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setProfilePicture(fileName);

        try {
            userRepository.save(user);
            log.info("Usuario {} criado com sucesso", user.getUsername());
        } catch (RuntimeException e) {
            log.warn("Erro ao salvar usuário: {}", user.getUsername(), e);
            throw new RuntimeException("Erro ao salvar usuario", e);
        }

        return "Salvo com sucesso.";
    }

    public List<UserRegisterDTO> getAllUsers(){
        log.info("Carregando todos os usuarios...");
        List<UserRegisterDTO> userRegisterDTOS = new ArrayList<>();
        try {
            List<User> users = userRepository.findAll();
            for (User user : users){
                UserRegisterDTO userRegisterDTO = new UserRegisterDTO(user);
                userRegisterDTOS.add(userRegisterDTO);
            }

        } catch (RuntimeException e) {
            log.warn("Erro ao carregar usuarios", e);
            throw new RuntimeException("Erro ao carregar usuarios", e);
        }

        return userRegisterDTOS;
    }

    public void UpdateUser(User userAuthenticated, UpdateUserDTO dto, MultipartFile file) throws IOException {

        log.info("Tentando atualizar os dados do user {}", userAuthenticated.getId());
        User user = userRepository.findById(userAuthenticated.getId())
                .orElseThrow(()->{
                    log.error("Não foi possível atualizar utilizador com id {}", userAuthenticated.getId());
                    return new UsernameNotFoundException("Não foi possível atualizar o utilizador");
                });

        String username = dto.getUsername().trim().toLowerCase();
        String email = dto.getEmail().trim().toLowerCase();

        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(username, user.getId())) {
            throw new UsernameOrEmailExistException("Já existe outro utilizador com esse username");
        }

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(email, user.getId())) {
            throw new UsernameOrEmailExistException("Já existe outro utilizador com esse e-mail");
        }

        //Salvando imagem de perfil
        if (file != null && !file.isEmpty()){

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/users");

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            Files.copy(
                    file.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            user.setProfilePicture(fileName);
        }

        try {
            user.setUsername(dto.getUsername());
            user.setName(dto.getName());
            user.setSurname(dto.getSurname());
            user.setEmail(dto.getEmail());
            user.setBirthDate(dto.getBirthDate());

            userRepository.save(user);
            log.info("Dados do user {} foram atualizados com sucesso", user.getId());

        } catch (RuntimeException e){
            log.error("Ocorreu um erro ao salvar alterações no banco. Erro: {}", e.getMessage());
            throw new SaveException("Erro ao salvar alterações no banco");
        }

    }

    private boolean thisEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    private boolean thisUsernameExists(String username){
        return userRepository.existsByUsername(username);
    }
    // verifica se existe um utilizador autenticado e se possui role ADMIN

    private boolean isAdmin(){

        User user = authenticatedUser.getAuthenticatedUser();
        return user != null
                && user
                .getRoles().stream()
                .anyMatch(role -> role.getAuthority() == RoleValue.ADMIN);
    }

    public long totalUsers() {
        return userRepository.count();
    }
}
