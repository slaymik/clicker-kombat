package ru.rsc.clicker_kombat.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import ru.rsc.clicker_kombat.utils.Roles;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "users")
@Builder
public class UserCredentials {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    String role;

    public static UserCredentials fromUserDetails(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String role = Roles.getRoleFromAuthorities(userDetails.getAuthorities());

        return UserCredentials.builder().username(username).role(role).build();
    }

}