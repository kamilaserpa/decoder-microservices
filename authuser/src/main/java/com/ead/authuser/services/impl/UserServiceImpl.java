package com.ead.authuser.services.impl;

import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Informa ao Spring que ele deve gerenciar esta classe, e gerar pontos de injeção
public class UserServiceImpl implements UserService {

    @Autowired // Ponto de injeção do Repository utilizando @Autowired
    UserRepository userRepository;

}
