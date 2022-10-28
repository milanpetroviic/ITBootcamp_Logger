package com.example.Logger_Application.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;
import javax.validation.constraints.*;
@Data
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long clientId;
    @Size(min = 3,message = "Username must be at least 3 characters long.")
    private String username;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password must contain at least: 8 characters, one lower case letter," +
                    " one upper case letter, one number and one special character.")
    private String password;
    private ClientRole role;
    private String token;
    @PrePersist
    private void setUserRoleAndToken(){
        this.setRole(ClientRole.USER);
        this.setToken(UUID.randomUUID().toString());
    }
}