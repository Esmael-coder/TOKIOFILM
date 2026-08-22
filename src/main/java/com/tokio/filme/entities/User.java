package com.tokio.filme.entities;

import com.tokio.filme.dtos.UserRegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String surname;

    @Column(unique = true)
    private String email;

    private String profilePicture;
    private Date birthDate;

    @CreationTimestamp
    private LocalDate creationDate;
    private LocalDateTime lastLogin;
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user") // 1 ‘user’ pode ter muitos filmes
    private List<Film> films = new ArrayList<>();

    @OneToMany(mappedBy = "user") // 1 ‘user’ pode ter muitos scores
    private List<Score> scores = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    public User(UserRegisterDTO userRegisterDTO) {
        this.username = userRegisterDTO.getUsername().toLowerCase();
        this.password = userRegisterDTO.getPassword();
        this.surname = userRegisterDTO.getSurname();
        this.name = userRegisterDTO.getName();
        this.email = userRegisterDTO.getEmail().toLowerCase();
        this.birthDate = userRegisterDTO.getBirthDate();
    }
}
