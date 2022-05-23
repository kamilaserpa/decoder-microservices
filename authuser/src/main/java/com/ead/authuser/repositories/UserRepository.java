package com.ead.authuser.repositories;

import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Quando utilizamos o JpaRepository o Spring já reconhece o Bean que é gerenciado por ele,
// não sendo 'estritamente' necessária a anotação de @Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

}
