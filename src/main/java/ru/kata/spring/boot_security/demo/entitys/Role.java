package ru.kata.spring.boot_security.demo.entitys;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 4, max = 20)
    @Column(name = "name", nullable = false, unique = true)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {

    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return roleName;
    }

    public void setName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    @Override
    public String toString() {
        return "Role{id=" + id + ", role='" + roleName + "'}";
    }

    public String getFormattedName() {
        return roleName.replace("ROLE_", "");
    }
}