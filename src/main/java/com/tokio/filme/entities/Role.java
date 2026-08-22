package com.tokio.filme.entities;

import com.tokio.filme.enuns.RoleValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleValue authority;

}
