package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora propriedades com valores nullos na serialização para Json
public class UserDto {

    // Ao inserir estas interfaces nos métodos de controller, o recurso passa a gerenciar apenas
    // as propriedades anotadas com ele.
    public interface UserView {
        public static interface RegistrationPost {} // alteracao no momento do cadastro
        public static interface UserPut {} // alteracao na atualizacao do usuário
        public static interface PasswordPut {} // alteracao de password
        public static interface ImagePut {} // alteracao de imagem
    }

    @JsonView(UserView.RegistrationPost.class) // alterado apenas no momento do cadastro
    private String username;

    @JsonView(UserView.RegistrationPost.class)
    private String email;

    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;

    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

}
