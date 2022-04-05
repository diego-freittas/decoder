package com.ead.authuser.dtos;

import com.ead.authuser.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public interface UserView {
        public static interface RegistrarionPost{}
        public static interface UserPut{}
        public static interface PasswordPut{}
        public static interface ImagePut{}
    }

    private UUID userId;

    @NotBlank(groups = UserView.RegistrarionPost.class)
    @Size(min=4, max=20, groups = UserView.RegistrarionPost.class)
    @UsernameConstraint(groups = UserView.RegistrarionPost.class)
    @JsonView(UserView.RegistrarionPost.class)
    private String username;

    @NotBlank(groups = UserView.RegistrarionPost.class)
    @Email(groups = UserView.RegistrarionPost.class)
    @JsonView(UserView.RegistrarionPost.class)
    private String email;

    @NotBlank(groups = {UserView.RegistrarionPost.class,UserView.PasswordPut.class})
    @Size(min=4, max=20, groups = {UserView.RegistrarionPost.class,UserView.PasswordPut.class})
    @JsonView({UserView.RegistrarionPost.class, UserView.PasswordPut.class})
    private String password;

    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min=4, max=20, groups = UserView.PasswordPut.class)
    @JsonView({UserView.PasswordPut.class})
    private String oldPassword;

    @JsonView({UserView.RegistrarionPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.RegistrarionPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @JsonView({UserView.RegistrarionPost.class, UserView.UserPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView({UserView.ImagePut.class})
    private String imageUrl;
}
